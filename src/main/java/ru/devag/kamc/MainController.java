package ru.devag.kamc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
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
    public String importBook(@RequestBody Map<String, Integer> sheetCodes) throws InterruptedException {
        BookInfo bookInfo = importSvc.get("1");
        if (bookInfo == null) {
            return "Книга не загружена";
        }

        if (impResult != null && !impResult.isDone()) {
            return "Предыдущий импорт еще не завершен";
        }
        
        List<SheetInfo> sheets = new ArrayList<>();
        for (SheetInfo sheet: bookInfo.sheets) {
            if (sheetCodes.containsKey(sheet.sheetName)) {
                sheets.add(sheet);
            }
        }
        impResult = importSvc.importSheets(sheets);
        return "OK";
    }
}
