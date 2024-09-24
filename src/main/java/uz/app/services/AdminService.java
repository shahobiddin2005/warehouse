package uz.app.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.entity.User;
import uz.app.entity.Warehouse;
import uz.app.enums.Role;
import uz.app.enums.Status;
import uz.app.repository.UserRepository;
import uz.app.repository.WarehouseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;


    public String editUser(Optional<User> optionalUser, String role, String status) {
        String redirect = "redirect:/admin";
        System.out.println(role);
        System.out.println(status);
        System.out.println(optionalUser.isPresent());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            redirect = redirect + (user.getRole().equals(Role.MANAGER) ? "/manager" : "/user");
            if (user.getRole().equals(Role.ANONYMOUS_USER)) return redirect;
            user.setStatus(Enum.valueOf(Status.class, status));
            user.setRole(Enum.valueOf(Role.class, role));
            userRepository.save(user);
        }
        return redirect;
    }

    public String addWarehouse(String userId, String name, String address) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(Role.MANAGER);
            userRepository.save(user);
            Warehouse warehouse = Warehouse.builder()
                    .name(name)
                    .address(address)
                    .phone(user.getPhone())
                    .manager(user)
                    .balance(0.0)
                    .build();
            warehouseRepository.save(warehouse);
        }
        return "redirect:/admin/warehouse";
    }
}
