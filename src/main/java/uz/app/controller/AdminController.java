package uz.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.app.entity.User;
import uz.app.enums.Role;
import uz.app.repository.UserRepository;
import uz.app.services.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final AdminService adminService;

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

    @GetMapping("/user/set")
    public ModelAndView setUser(@RequestParam String id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("edit-user");
        mav.addObject("id", id);
        return mav;
    }

    @PostMapping("/user/set")
    public String setUser(@RequestParam String id, @RequestParam String role, @RequestParam String status) {
        return adminService.editUser(userRepository.findById(id), role, status);
    }
}
