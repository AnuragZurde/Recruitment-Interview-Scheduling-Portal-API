package com.company.dto.InterviewDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewRequestDto {

    @NotBlank(message = "Interviewer email is required.")
    private String interviewerEmail;

    @NotNull(message = "Schedule time is required.")
    @Future(message = "Interview time must be in future")
    private LocalDateTime scheduleTime;

    @NotBlank(message = "Meeting Link Is required.")
    private String meetingLink;
}
