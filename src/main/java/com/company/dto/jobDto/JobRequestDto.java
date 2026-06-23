package com.company.dto.jobDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDto {

    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull
    @Min(value = 0, message = "Minimum salary must be Positive")
    private Double minSalary;

    @NotNull
    @Min(value = 0, message = "Maximum Salary Must be Positive")
    private Double maxSalary;

    @NotEmpty(message = "At least one skills required")
    private Set<String> skills;
}