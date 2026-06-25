package com.company.dto.InterviewDto;

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
public class InterviewFeedbackDto {
    @NotBlank(message = "Feedback cannot be empty.")
    private String feedBack;

    @NotNull(message = "Must declared if Candidate passed or failed.")
    private Boolean passed;
}
