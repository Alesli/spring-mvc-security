package com.training.security.service;

import com.training.security.model.UserEntity;
import com.training.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("UserEntity not found");
        } else {
            if (loginAttemptService.isBlocked(username)) {
                throw new LockedException("User is blocked");
            }
        }

        String[] authorities = user.getUserAuthorities().split(";");

        return User.withUsername(user.getUserName()).password(user.getUserPassword()).authorities(authorities).build();
    }
}

