package uz.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.app.entity.User;
import uz.app.enums.Role;
import uz.app.enums.Status;
import uz.app.repository.UserRepository;
import uz.app.repository.WarehouseRepository;
import uz.app.services.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
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
        System.out.println(id);
        return adminService.editUser(userRepository.findById(id), role, status);
    }

    @GetMapping("/warehouse")
    public ModelAndView warehouses() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("warehouse-page");
        mav.addObject("warehouses", warehouseRepository.findAll());
        return mav;
    }

    @GetMapping("/warehouse/add")
    public ModelAndView warehouse() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("warehouse-add");
        List<User> users = userRepository.findAllByRoleAndStatus(Role.USER, Status.ACTIVE);
        if (users.isEmpty()){
            mav.setViewName("error-page");
            mav.addObject("message", "No active users");
            mav.addObject("link", "/admin");
        }
        else
            mav.addObject("users", users);
        return mav;
    }

    @PostMapping("/warehouse/add")
    public String warehouseAdd(@RequestParam String user, @RequestParam String name, @RequestParam String address) {
        return adminService.addWarehouse(user, name, address);
    }
}
