package com.company.dto.applicationDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationRequestDto {

    @NotBlank(message = "Resume URL Is Required.")
    private String resumeUrl;
}
