package com.company.dto.applicationDto;

import com.company.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponseDto {

    private Long applicationId;
    private String candidateName;
    private String candidateEmail;
    private String jobTitle;
    private ApplicationStatus status;
    private String resumeUrl;
    private LocalDateTime appliedAt;
}
