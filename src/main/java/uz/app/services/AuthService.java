package uz.app.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.config.Context;
import uz.app.entity.User;
import uz.app.enums.Role;
import uz.app.enums.Status;
import uz.app.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public boolean signUp(String name, String surname, String phone, String password) {
        phone = phoneFormat(phone);
        User user = User.builder()
                .name(name)
                .surname(surname)
                .phone(phone)
                .password(password)
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();
        Optional<User> optionalUser = userRepository.findByPhone(phone);
        if (optionalUser.isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    private String phoneFormat(String phone) {
        StringBuilder sb = new StringBuilder("+");
        for (char c : phone.toCharArray()) {
            if (Character.isDigit(c))
                sb.append(c);
        }
        return sb.toString();
    }

    public String  signIn(String phone, String password, HttpServletRequest req) {
        Optional<User> optionalUser = userRepository.findByPhone(phoneFormat(phone));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                if (user.getStatus().equals(Status.ACTIVE)){
                    Context.setUser(user);
                    HttpSession session = req.getSession();
                    session.setAttribute("userId", user.getId());
                    if (user.getRole().equals(Role.ADMIN)){
                        return "redirect:/admin";
                    }else if (user.getRole().equals(Role.USER)){
                        return "redirect:/user";
                    }else if (user.getRole().equals(Role.MANAGER)){
                        return "redirect:/manager";
                    }else if (user.getRole().equals(Role.COURIER)){
                        return "redirect:/courier";
                    }else {
                        return "redirect:/home";
                    }
                }else {
                    HttpSession session = req.getSession();
                    session.removeAttribute("userId");
                    return "redirect:/auth/user-blocked";
                }
            }
        }
        return "redirect:/auth/sign-in";
    }
}
