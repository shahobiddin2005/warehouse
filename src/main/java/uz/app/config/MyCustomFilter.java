package uz.app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.app.entity.User;
import uz.app.enums.Role;
import uz.app.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MyCustomFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();

        HttpSession session = req.getSession();

        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            if (requestURI.contains("/auth")) {
                filterChain.doFilter(request, response);
            } else {
                resp.sendRedirect("/auth/sign-in");
            }
            return;
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Context.setUser(user);
            if (requestURI.contains("/cabinet")) {
                if (user.getRole().equals(Role.ADMIN)) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    resp.sendRedirect("/home");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
