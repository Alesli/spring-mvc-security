package com.training.security.controller;

import com.training.security.model.UserEntity;
import com.training.security.repository.UserRepository;
import com.training.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class MainSecurityController {

    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;


    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/about")
    public String getAboutInfo() {
        return "about";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login(final ModelMap model, @RequestParam("error") final Optional<String> error) {
        error.ifPresent(errorMsg -> model.addAttribute("error", errorMsg));
        return "login";
    }

    @GetMapping("/logoutSuccess")
    public String logoutSuccess() {
        return "logout";
    }

    @GetMapping("/blocked")
    public String blocked(Model model) {
        List<UserEntity> UserEntities = userRepository.findAll();
        Map<String, LocalDateTime> blockedUsers = UserEntities.stream()
                .map(UserEntity::getUserName)
                .filter(userName -> loginAttemptService.isBlocked(userName))
                .collect(Collectors.toMap(user -> user, user -> loginAttemptService.getCachedValue(user).getBlockedTimeStamp()));

        if (!blockedUsers.isEmpty()) {
            model.addAttribute("blockedUsers", blockedUsers);
        }
        return "blocked";
    }
}
