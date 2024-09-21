package uz.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.app.config.Context;
import uz.app.entity.User;
import uz.app.services.AuthService;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @GetMapping
    private String toAuth() {
        if (Context.getUser() != null)return "redirect:/cabinet";
        return "redirect:/auth/sign-in";
    }

    @GetMapping("/sign-up")
    private String toSignUp() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    private String toSignUp(@RequestParam String name, @RequestParam String surname, @RequestParam String phone, @RequestParam String password) {
        if (authService.signUp(name, surname, phone, password)) {
            return "sign-in";
        }else {
            return "sign-up";
        }
    }

    @GetMapping("/sign-in")
    private String toSignIn() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    private String toSignIn(@RequestParam String phone, @RequestParam String password) {
        if (authService.signIn(phone, password)) {
            return "redirect:/cabinet";
        }else {
            return "sign-in";
        }
    }
}
