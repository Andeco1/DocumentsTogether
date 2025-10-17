package ru.together.documents.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class BaseController {
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }
    @GetMapping("/register")
    public String showRegister(){
        return "register";
    }
}
