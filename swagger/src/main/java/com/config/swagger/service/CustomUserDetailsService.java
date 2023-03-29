package com.config.swagger.service;

import com.config.swagger.config.security.CustomUserDetails;
import com.config.swagger.entities.User;
import com.config.swagger.errors.ServiceException;
import com.config.swagger.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUserName(username.toLowerCase()).orElse(null);
        if (user == null) {
            throw ServiceException.badRequest(username+" Not found");
        }
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(Long userId) {
        if (userId == null) {
            throw new UsernameNotFoundException("Error");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(userId.toString()));
        return new CustomUserDetails(user);
    }
}
