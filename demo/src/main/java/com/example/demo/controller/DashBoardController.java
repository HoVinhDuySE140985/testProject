package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashBoardController {
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "pages/dashboard";
    }

    @RequestMapping("/calendar")
    public String calendar() {
        return "pages/calendar";
    }

}