package uz.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.entity.User;
import uz.app.enums.Role;
import uz.app.enums.Status;
import uz.app.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;


    public String editUser(Optional<User> optionalUser, String role, String status) {
        String redirect = "redirect:/admin";
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            redirect = redirect + (user.getRole().equals(Role.MANAGER) ? "/manager" : "/user");
            user.setStatus(Enum.valueOf(Status.class, status));
            if (user.getRole().equals(Role.ANONYMOUS_USER)) return redirect;
            user.setRole(Enum.valueOf(Role.class, role));
            userRepository.save(user);
        }
        return redirect;
    }
}
