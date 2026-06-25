package com.company.dto.InterviewDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewResponseDto {
    private Long interviewId;
    private String candidateName;
    private String jobTitle;
    private String interviewerName;
    private LocalDateTime scheduleTime;
    private String meetingLink;
    private Boolean passed;
    private String interviewerFeedback;
}
