package ru.devag.kamc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.devag.kamc.model.*;
import ru.devag.kamc.repo.*;

@RestController
public class MainController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    I3ObjectRepository i3Repo;

    @Autowired
    I3CntrComponentRepository cntrRepo;
    

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

        // Get the file and save it somewhere
        /*byte[] bytes = file.getBytes();
        Path path = Paths.get(file.getOriginalFilename());
        Files.write(path, bytes);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded '" + file.getOriginalFilename() + "'");*/

        InputStream is = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(is);

        BookInfo bookInfo = new BookInfo(workbook);
        
        workbook.close();
        is.close();

        for (SheetInfo sheet: bookInfo.sheets) {
            
        }

                           
        //return "redirect:/uploadStatus";
        return bookInfo;
    }

    

    
}
