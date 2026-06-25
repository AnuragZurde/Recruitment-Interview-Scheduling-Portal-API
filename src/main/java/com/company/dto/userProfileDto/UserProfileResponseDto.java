package com.company.dto.userProfileDto;

import com.company.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponseDto {
    private Long userId;
    private String fullName;
    private String email;
    private Long mobileNumber;
    private UserRole role;
}
