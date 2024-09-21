package uz.app.services;

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

    public boolean signIn(String phone, String password) {
        Optional<User> optionalUser = userRepository.findByPhone(phoneFormat(phone));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)&& user.getStatus().equals(Status.ACTIVE)) {
                Context.setUser(user);
                return true;
            }
        }
        return false;
    }
}
