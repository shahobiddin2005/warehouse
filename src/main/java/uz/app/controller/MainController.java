package uz.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.app.config.Context;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String index() {
        if (Context.getUser() != null)return "redirect:/cabinet";
        return "index";
    }
}
