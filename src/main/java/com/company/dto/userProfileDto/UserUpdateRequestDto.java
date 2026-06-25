package com.company.dto.userProfileDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequestDto {

    @NotBlank(message = "User Full Name Can not be null.")
    private String fullName;

    @NotNull(message = "Mobile Number Can not be null.")
    private Long mobileNumber;
}
