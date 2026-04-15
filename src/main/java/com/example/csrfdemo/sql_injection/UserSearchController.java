package com.example.csrfdemo.sql_injection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class UserSearchController {

    private final JdbcTemplate jdbcTemplate;

    public UserSearchController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String username, Model model) {

        if (username != null) {

            // ❌ SQL Injection (конкатенация строк)
            String sql = "SELECT id, username, email FROM users WHERE username = '"
                    + username + "'";

            List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
            model.addAttribute("users", users);
        }

        return "search";
    }
}