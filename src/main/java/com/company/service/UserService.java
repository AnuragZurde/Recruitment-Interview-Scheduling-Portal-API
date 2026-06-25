package com.company.service;

import com.company.dto.userProfileDto.UserProfileResponseDto;
import com.company.dto.userProfileDto.UserUpdateRequestDto;
import com.company.entity.User;
import com.company.mapper.UserMapper;
import com.company.reppository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserProfileResponseDto getUserProfile (String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found."));

        return userMapper.toDto(user);
    }

    @Transactional
    public UserProfileResponseDto updateUserProfile (String email, @Valid UserUpdateRequestDto request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found."));

        user.setFullName(request.getFullName());
        user.setMobileNumber(request.getMobileNumber());

        User updateUser = userRepository.save(user);
        return userMapper.toDto(updateUser);
    }

    @Transactional
    public String deleteUserAccount (String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found."));

        userRepository.delete(user);
        return "Account Deleted Successfully.";
    }

    @Transactional
    public UserProfileResponseDto patchUserAccount (String email, @Valid Map<String, Object> request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found."));

        request.forEach((field, data)->{
            switch (field){
                case "fullName" -> user.setFullName((String) data);
                case "mobileNumber" -> user.setMobileNumber(Long.valueOf(data.toString()));

                default -> throw new IllegalArgumentException("Invalid User Input.");
            }
        });

        User updateUser = userRepository.save(user);
        return userMapper.toDto(updateUser);
    }
}
