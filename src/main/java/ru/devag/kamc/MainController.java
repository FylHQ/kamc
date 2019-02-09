package ru.devag.kamc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

    //ConcurrentHashMap<String, CompletableFuture<String>> impResults = new ConcurrentHashMap<>();
    CompletableFuture<String> impResult = null;
    

    @RequestMapping("/cntr")

    public String cntr() {
        Page<I3CntrComponent> cntrs = cntrRepo.findAll(PageRequest.of(1, 20, Sort.by("cnrNumber")));
        String s = "";
        for (I3CntrComponent cntr: cntrs) {
            s += cntr.getCnrNumber() + "<br/>";
        }
        return s;
    }

    @RequestMapping("/log")
    public List<LogItem> testLog(@RequestParam(required = false) Long from) {
        //LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        //ListAppender<ILoggingEvent> listAppender = (ListAppender<ILoggingEvent>)context.getLogger(Logger.ROOT_LOGGER_NAME).getAppender("List");
        MemoryAppender instance = MemoryAppenderInstance.getInstance();
        
        return instance.getEvents().stream()
            .filter(e -> from == null || e.getTimeStamp() > from)
            .map(e -> new LogItem(e))
            .collect(Collectors.toList());
        
        //ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        
        //ListAppender<ILoggingEvent> listAppender = (ListAppender<ILoggingEvent>)rootLogger.getAppender("List");
        //return listAppender.list.size();
        //(ListAppender) root
        //.getAppender("LIST");
    }

  @RequestMapping("/obj/count")
    public String index() {
        //return i3Repo.count();

        Optional<I3CntrComponent> optCntr = cntrRepo. findById(29454053L);
        if (optCntr.isPresent())
            return optCntr.get().getCnrDescription();
        
        return "none";

        /*I3Object obj = i3Repo.findByObjNumber("ПМ-3660");
        I3Object obj2 = new I3Object();
        obj2.setCatCategoryId(obj.getCatCategoryId());
        obj2.setObjNumber("QQQ" + System.currentTimeMillis());
        i3Repo.save(obj2);
        return obj2.getObjObjectId();*/
        //3
        //return jdbcTemplate.queryForObject("select count(1) from i3_object", Integer.class);
    }

    @PostMapping("/upload")
    public BookInfo singleFileUpload(@RequestParam("xlsx") MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(is);

        BookInfo bookInfo = new BookInfo(workbook);

        workbook.close();
        is.close();

        bookInfo.sheets.forEach(sheet -> {
            if (bstRepo.findByBstNumber(sheet.cntrNum).isPresent()) {
                sheet.isExists = true;
            }
        });

        importSvc.put("1", bookInfo);

        return bookInfo;
    }

    /*@GetMapping("/check/{sheetName}")
    public String check(@PathVariable String sheetName) {
        CompletableFuture<String> future = impResults.get(sheetName);
        if (future != null)
            return future.isDone() ? "DONE" : "BUSY";
        else
            return "NOT_FOUND";
    }*/

    @GetMapping("/checkall")
    public String checkAll() {
        if (impResult != null)
            return impResult.isDone() ? "DONE" : "BUSY";
        else
            return "NOT_FOUND";
    }

    @PostMapping("/import")
    public String importBook(@RequestBody ImportRequest ir) throws InterruptedException {
        BookInfo bookInfo = importSvc.get("1");
        if (bookInfo == null) {
            return "Книга не загружена";
        }

        /*if (impResult != null && !impResult.isDone()) {
            return "Предыдущий импорт еще не завершен";
        }*/
        
        List<String> ignored = new ArrayList<>();
        List<SheetInfo> sheets = new ArrayList<>();
        for (SheetInfo sheet: bookInfo.sheets) {
            if (ir.sheetCodes.containsKey(sheet.sheetName)) {
                if (StringUtils.isEmpty(sheet.inn)) {
                    logger.error("Не указан ИНН: {}", sheet.subject);
                } else {
                    try {
                        importSvc.importSheet(sheet, 
                            ir.settings.getOrDefault("ignoreCheap", false),
                            ir.settings.getOrDefault("ignoreAll", false),
                            ignored);
                        logger.info("Импорт [{}]: OK", sheet.cntrNum);
                     } catch (Exception e) {
                        logger.error("Ошибка импорта [{}]: {}", sheet.cntrNum, e.getLocalizedMessage());
                     }
                }
                
                sheets.add(sheet);
            }
        }
        if (ignored.size() > 0) {
            logger.debug("Пропущено имущества: {}", ignored.size());
            ignored.forEach(item -> logger.debug(item));
        }

        //return importSvc.importSheets(sheets);
        return "OK";
    }

    @GetMapping("/test1")
    public int test1() {
        /*Query q = em.createQuery("select obj from I3Object obj " +
            "join I3ObjRtn obr on obj.id = obr.objObjectId " + 
            "join I3SbjRtn sbr on obr.rtnRelationId = sbr.rtnRelationId " + 
            "where sbr.sbjSubjectId = :sbjId");
        q.setParameter("sbjId", 30645764L);
        List<I3Object> obj = q.getResultList();*/
        //List<I3Object> objs = objRepo.findByRtnSbj(30645764L);
        //Map<String, List<I3Object>> result =
        //    objs.stream().collect(Collectors.groupingBy(I3Object::getObjDescription));
        //Map<String, List<Long>> descrIds = objs.stream().

        //objs.stream().forEach(action);
        //logger.info("{}", obj.get(0)[1]);
        //List<I3Object> obj = commonRepo.find1(30645764L);

        logger.info("Start last cost");
        List<Object[]> objs = objRepo.getAllLastCost();
        logger.info("End last cost");

        List<Object[]> newObj = objs
        .stream()
        .parallel()
        .filter(item -> item[1] != null && ((BigDecimal)item[1]).doubleValue() == 2280551.57)
        .collect(Collectors.toList());
        logger.info("Found last cost: {}", newObj.size());
        



        return newObj.size();
    }

    @GetMapping("/test2")
    public Long test2() {
        //List<I3AprmComponent> aprms = aprmRepo.findByApmCadastralInfoNotNull();
        //return aprms.get(0).getObject().getId();
        //Optional<I3AprmComponent> a = aprmRepo.findByObjectObjNumber("ПМ-3164");
        //return a.get().getId();

        
        
        //List<Tuple> obj = netwRepo.findObjectsNetCadastralCostNotNull();
        //return obj.get(0).get(1, I3Object.class).getId();

        importSvc.initConstants();
        
        //logger.info("{}", importSvc.netwCadObjIds.size());
        return 1L;
        
        //List<I3NetwComponent> netws = netwRepo.find2();
        //return netws.get(0).getLand().getId();
    }
    
}
