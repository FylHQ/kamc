package ru.devag.kamc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/obj/count")
    public int index() {
        return jdbcTemplate.queryForObject("select count(1) from i3_object", Integer.class);
    }
}