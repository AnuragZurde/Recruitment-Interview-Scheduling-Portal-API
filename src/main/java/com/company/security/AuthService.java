package com.company.security;

import com.company.dto.userDto.LoginRequestDto;
import com.company.dto.userDto.LoginResponseDto;
import com.company.dto.userDto.SignupRequestDto;
import com.company.dto.userDto.SignupResponseDto;
import com.company.entity.User;
import com.company.reppository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public @Nullable SignupResponseDto signup (@Valid SignupRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user != null ) throw new IllegalArgumentException("User Already Present.");

        user = userRepository.save(User.builder()
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                        .email(request.getEmail())
                        .mobileNumber(request.getMobileNumber())
                .userRole(request.getUserRole())
                .build());

        return new SignupResponseDto(user.getId(), user.getFullName());
    }

    public @Nullable LoginResponseDto login (LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String jwtToken = jwtUtil.generateAccessToken(user);

        return new LoginResponseDto(user.getId(), user.getUsername(), jwtToken);
    }
}
