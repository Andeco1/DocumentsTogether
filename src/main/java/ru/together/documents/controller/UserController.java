package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.together.documents.service.UserService;

@Controller
@RequestMapping("/docs")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes
    ) {
        boolean success = userService.register(username, email, password, confirmPassword);

        if (success) {
            redirectAttributes.addAttribute("success", true);
        } else {
            redirectAttributes.addAttribute("error", true);
        }

        return "redirect:/docs/register";
    }

    @PostMapping("/login")
    public String showLogin(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes
    ){
        boolean success = userService.login(username,password);
        if(success){
            redirectAttributes.addAttribute("username",username);
            return "redirect:/library";
        } else {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/docs/login";
        }
    }
}
