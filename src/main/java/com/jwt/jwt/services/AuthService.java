package com.jwt.jwt.services;

import com.jwt.jwt.dao.UserRepository;
import com.jwt.jwt.dto.request.RegisterDTO;
import com.jwt.jwt.entity.Role;
import com.jwt.jwt.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public void save(RegisterDTO userDTO, List<Role> roles){

        User user = User.builder()
                .Username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .password(encoder.encode(userDTO.getPassword()))
                .isActive(true)
                .roles(roles)
                .build();
        userRepository.save(user);
    }
}
