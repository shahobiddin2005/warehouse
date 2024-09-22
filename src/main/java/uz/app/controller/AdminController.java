package uz.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uz.app.entity.User;
import uz.app.enums.Role;
import uz.app.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;

    @GetMapping
    public String welcome() {
        return "admin-cabinet";
    }

    @GetMapping("/user")
    public ModelAndView users() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-cabinet");
        List<User> allByRole = userRepository.findAllByRole(Role.ANONYMOUS_USER);
        allByRole.addAll(userRepository.findAllByRole(Role.USER));
        mav.addObject("users", allByRole);
        return mav;
    }

    @GetMapping("/manager")
    public ModelAndView manager() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-cabinet");
        mav.addObject("users", userRepository.findAllByRole(Role.MANAGER));
        return mav;
    }

    @GetMapping("/courier")
    public ModelAndView courier() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-cabinet");
        mav.addObject("users", userRepository.findAllByRole(Role.COURIER));
        return mav;
    }
}
