package com.company.controller;

import com.company.dto.userProfileDto.UserProfileResponseDto;
import com.company.dto.userProfileDto.UserUpdateRequestDto;
import com.company.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserProfileResponseDto> getMyProfile(Authentication authentication ){
        UserProfileResponseDto profile = userService.getUserProfile(authentication.getName());

        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<UserProfileResponseDto> updateMyProfile(@Valid @RequestBody UserUpdateRequestDto request,
                                                                  Authentication authentication){
        UserProfileResponseDto profile = userService.updateUserProfile(authentication.getName(), request);

        return ResponseEntity.ok(profile);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMyAccount(Authentication authentication){
        String response = userService.deleteUserAccount(authentication.getName());

        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<UserProfileResponseDto> updateInPatchDetails(@Valid @RequestBody Map<String, Object> request, Authentication authentication){
        UserProfileResponseDto response = userService.patchUserAccount(authentication.getName(), request);

        return ResponseEntity.ok(response);
    }
}
