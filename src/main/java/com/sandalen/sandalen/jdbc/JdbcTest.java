package com.sandalen.sandalen.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class JdbcTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @ResponseBody
    @GetMapping("/jdbc")
    public String jdbcTest() throws SQLException {
        System.out.println("class=="+dataSource.getClass());
        System.out.println("connection=="+dataSource.getConnection());

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from rpt_area_analyse");

        System.out.println(list.get(0).get("cityname"));

        return list.get(0).toString();
    }
}
