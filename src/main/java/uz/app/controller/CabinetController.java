package uz.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.app.config.Context;
import uz.app.enums.Role;

@Controller
@RequestMapping("/cabinet")
public class CabinetController {
    @GetMapping
    public String cabinet() {
        System.out.println(Context.getUser());
        if ( Context.getUser() != null) {
            if (Context.getUser().getRole().equals(Role.ADMIN))return "admin-cabinet";
            else if (Context.getUser().getRole().equals(Role.USER))return "user-cabinet";
            else if (Context.getUser().getRole().equals(Role.COURIER))return "courier-cabinet";
            else return "redirect:/auth";
        }else {
            return "redirect:/auth";
        }
    }
}
