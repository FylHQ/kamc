package ru.devag.kamc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.devag.kamc.log.*;
import ru.devag.kamc.model.*;
import ru.devag.kamc.rent.*;
import ru.devag.kamc.nto.*;
import ru.devag.kamc.repo.*;

@RestController
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    I3ObjectRepository i3Repo;

    @Autowired
    I3CntrComponentRepository cntrRepo;

    @Autowired
    ImportService importSvc;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    I3ObjectRepository objRepo;

    @Autowired
    I3BasementRepository bstRepo;

    @Autowired
    I3AprmComponentRepository aprmRepo;

    @Autowired
    I3NetwComponentRepository netwRepo;

    @Autowired
    I3RelationRepository rtnRepo;

    // ConcurrentHashMap<String, CompletableFuture<String>> impResults = new
    // ConcurrentHashMap<>();
    CompletableFuture<String> impResult = null;

    @RequestMapping("/cntr")

    public String cntr() {
        Page<I3CntrComponent> cntrs = cntrRepo.findAll(PageRequest.of(1, 20, Sort.by("cnrNumber")));
        String s = "";
        for (I3CntrComponent cntr : cntrs) {
            s += cntr.getCnrNumber() + "<br/>";
        }
        return s;
    }

    @RequestMapping("/log")
    public List<LogItem> testLog(@RequestParam(required = false) Long from) {
        // LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        // ListAppender<ILoggingEvent> listAppender =
        // (ListAppender<ILoggingEvent>)context.getLogger(Logger.ROOT_LOGGER_NAME).getAppender("List");
        MemoryAppender instance = MemoryAppenderInstance.getInstance();

        return instance.getEvents().stream().filter(e -> from == null || e.getTimeStamp() > from)
                .map(e -> new LogItem(e)).collect(Collectors.toList());

        // ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger)
        // LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        // ListAppender<ILoggingEvent> listAppender =
        // (ListAppender<ILoggingEvent>)rootLogger.getAppender("List");
        // return listAppender.list.size();
        // (ListAppender) root
        // .getAppender("LIST");
    }

    @RequestMapping("/obj/count")
    public String index() {
        // return i3Repo.count();

        Optional<I3CntrComponent> optCntr = cntrRepo.findById(29454053L);
        if (optCntr.isPresent())
            return optCntr.get().getCnrDescription();

        return "none";

        /*
         * I3Object obj = i3Repo.findByObjNumber("ПМ-3660"); I3Object obj2 = new
         * I3Object(); obj2.setCatCategoryId(obj.getCatCategoryId());
         * obj2.setObjNumber("QQQ" + System.currentTimeMillis()); i3Repo.save(obj2);
         * return obj2.getObjObjectId();
         */
        // 3
        // return jdbcTemplate.queryForObject("select count(1) from i3_object",
        // Integer.class);
    }

    @PostMapping("/upload")
    public BookInfo<?> singleFileUpload(@RequestParam("xlsx") MultipartFile file, @RequestParam("sourceType") String sourceType) throws IOException {
        InputStream is = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(is);

        BookInfo<?> rentBook;
        if (sourceType.equals("rent")) {
            rentBook = new RentBook(workbook);

            rentBook.sheets.forEach(sheet -> {
                if (bstRepo.findByBstNumber(((RentSheet)sheet).cntrNum).isPresent()) {
                    ((RentSheet)sheet).isExists = true;
                }
            });
    
            importSvc.put("rent", rentBook);
        } else if (sourceType.equals("nto")) {
            rentBook = new NtoBook(workbook); 
        } else {
            logger.error("Unsupported source type: {}", sourceType);
            rentBook = null;
        }

        workbook.close();
        is.close();

        /*AtomicInteger count = new AtomicInteger(0);
        rentBook.sheets.forEach(sheet -> {
            sheet.property.forEach(prop -> {
                if (prop.propType == PropType.NETW)
                    count.incrementAndGet();
            });
        });
        logger.error("netw {}", count.get());*/

        return rentBook;
    }

    /*
     * @GetMapping("/check/{sheetName}") public String check(@PathVariable String
     * sheetName) { CompletableFuture<String> future = impResults.get(sheetName); if
     * (future != null) return future.isDone() ? "DONE" : "BUSY"; else return
     * "NOT_FOUND"; }
     */

    @GetMapping("/checkall")
    public String checkAll() {
        if (impResult != null)
            return impResult.isDone() ? "DONE" : "BUSY";
        else
            return "NOT_FOUND";
    }

    @PostMapping("/import")
    public String importBook(@RequestBody ImportRequest ir) throws InterruptedException {
        RentBook rentBook = (RentBook) importSvc.get("rent");
        if (rentBook == null) {
            return "Книга не загружена";
        }

        /*
         * if (impResult != null && !impResult.isDone()) { return
         * "Предыдущий импорт еще не завершен"; }
         */
        importSvc.init(ir.settings);

        List<String> ignored = new ArrayList<>();
        List<RentSheet> sheets = new ArrayList<>();
        for (RentSheet sheet : rentBook.sheets) {
            if (ir.sheetCodes.containsKey(sheet.sheetName)) {
                if (StringUtils.isEmpty(sheet.inn)) {
                    logger.error("Не указан ИНН: {}", sheet.subject);
                } else {
                    try {
                        List<String> created = new ArrayList<>();
                        importSvc.importSheet(sheet, ignored, created);
                        logger.info("Импорт [{}]: OK", sheet.cntrNum);
                        if (created.size() > 0) {
                            logger.warn("Создано новых [{}]: {}", sheet.cntrNum, created.size());
                        }
                    } catch (Exception e) {
                        logger.error("Ошибка импорта [{}]: {}", sheet.cntrNum, e.getMessage());
                    }
                }

                sheets.add(sheet);
            }
        }
        if (ignored.size() > 0) {
            logger.debug("Пропущено имущества: {}", ignored.size());
            ignored.forEach(item -> logger.debug(item));
        }

        // return importSvc.importSheets(sheets);
        return "OK";
    }

    @GetMapping("/test1")
    public String test1() {
        /*
         * Query q = em.createQuery("select obj from I3Object obj " +
         * "join I3ObjRtn obr on obj.id = obr.objObjectId " +
         * "join I3SbjRtn sbr on obr.rtnRelationId = sbr.rtnRelationId " +
         * "where sbr.sbjSubjectId = :sbjId"); q.setParameter("sbjId", 30645764L);
         * List<I3Object> obj = q.getResultList();
         */
        // List<I3Object> objs = objRepo.findByRtnSbj(30645764L);
        // Map<String, List<I3Object>> result =
        // objs.stream().collect(Collectors.groupingBy(I3Object::getObjDescription));
        // Map<String, List<Long>> descrIds = objs.stream().

        // objs.stream().forEach(action);
        // logger.info("{}", obj.get(0)[1]);
        // List<I3Object> obj = commonRepo.find1(30645764L);

        // logger.info("Start last cost");
        // List<Tuple> objs = objRepo.getAllLastCost();
        // logger.info("End last cost");

        return StringUtils.substring("asdf", 0, 10);
    }

    @GetMapping("/test2")
    public Long test2() throws ParseException {
        //List<I3AprmComponent> aprms = aprmRepo.findByApmCadastralInfoNotNull();
        //return aprms.get(0).getObject().getId();
        //Optional<I3AprmComponent> a = aprmRepo.findByObjectObjNumber("ПМ-3164");
        //return a.get().getId();

        
        
        //List<Tuple> obj = netwRepo.findObjectsNetCadastralCostNotNull();
        //return obj.get(0).get(1, I3Object.class).getId();

        //importSvc.initConstants();
        I3Relation rtn = new I3Relation();
        rtn.setCatCategoryId(999L);
        rtn.setRtnDate(new SimpleDateFormat("dd.MM.yyyy").parse("12.01.1983"));
        rtn.setStsStatusId(2L);
        rtn.setRtnNumber("XLSX_2019_111");
        rtnRepo.save(rtn);
          
        //logger.info("{}", importSvc.netwCadObjIds.size());
        return 1L;
        
        //List<I3NetwComponent> netws = netwRepo.find2();
        //return netws.get(0).getLand().getId();
    }
    
}
