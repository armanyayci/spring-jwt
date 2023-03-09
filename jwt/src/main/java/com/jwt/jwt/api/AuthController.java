package com.jwt.jwt.api;

import com.jwt.jwt.dao.RoleRepository;
import com.jwt.jwt.dao.UserRepository;
import com.jwt.jwt.dto.request.LoginDTO;
import com.jwt.jwt.dto.request.RegisterDTO;
import com.jwt.jwt.dto.response.JwtResponse;
import com.jwt.jwt.dto.response.MessageResponse;
import com.jwt.jwt.entity.ERole;
import com.jwt.jwt.entity.Role;
import com.jwt.jwt.security.JwtTokenProvider;
import com.jwt.jwt.security.JwtUserDetails;
import com.jwt.jwt.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateJwtToken(authentication);

        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        List<String> strRoles = dto.getRole();
        List<Role> roles = new ArrayList<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN.name())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."+ERole.ROLE_ADMIN.name()));
                    roles.add(adminRole);

                    break;
                case "operator":
                    Role modRole = roleRepository.findByName(ERole.ROLE_OPERATOR.name())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."+ ERole.ROLE_OPERATOR.name()));
                    roles.add(modRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_TEAMLEADER.name())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."+ ERole.ROLE_TEAMLEADER.name()));
                    roles.add(userRole);
            }
        });
        authService.save(dto,roles);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }




}
