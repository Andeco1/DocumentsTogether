package ru.together.documents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class BaseController {
    @GetMapping
    public String showMainPage(){
        return "mainPage";
    }
}
