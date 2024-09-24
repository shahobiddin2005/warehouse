package uz.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.app.config.Context;
import uz.app.entity.User;
import uz.app.enums.Role;
import uz.app.services.AuthService;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @GetMapping
    private String toAuth() {
        if (Context.getUser() != null)return "redirect:/cabinet";
        User user = Context.getUser();
        if (user.getRole().equals(Role.ADMIN))return "redirect:/admin";
        else if (user.getRole().equals(Role.USER))return "redirect:/user";
        else if (user.getRole().equals(Role.COURIER))return "redirect:/courier";
        else if (user.getRole().equals(Role.MANAGER))return "redirect:/manager";
        return "redirect:/auth/sign-in";
    }

    @GetMapping("/sign-up")
    private String toSignUp() {
        return "sign-up";
    }

    @GetMapping("/user-blocked")
    private String userBlock() {
        return "user-block";
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
    private String toSignIn(@RequestParam String phone, @RequestParam String password, HttpServletRequest req) {
        return authService.signIn(phone, password, req);
    }
}
