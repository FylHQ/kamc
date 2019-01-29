package ru.devag.kamc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class MainController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/obj/count")
    public int index() {
        //3
        return jdbcTemplate.queryForObject("select count(1) from i3_object", Integer.class);
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
                           
        //return "redirect:/uploadStatus";
        return bookInfo;
    }

    

    
}
