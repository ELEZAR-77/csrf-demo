package com.example.csrfdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ProfileController {

    private String email = "user@example.com";

    @GetMapping("/")
    public String profile(Model model) {
        model.addAttribute("email", email);
        return "profile";
    }

    @PostMapping("/change-email")
    public String changeEmail(@RequestParam String email) {
        // ❌ CSRF-уязвимость (если защита отключена)
        this.email = email;
        return "redirect:/";
    }
}