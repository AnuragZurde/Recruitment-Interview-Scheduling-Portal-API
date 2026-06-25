package com.company.controller;

import com.company.dto.InterviewDto.InterviewFeedbackDto;
import com.company.dto.InterviewDto.InterviewRequestDto;
import com.company.dto.InterviewDto.InterviewResponseDto;
import com.company.service.InterviewerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewerService interviewerService;

    @GetMapping("/my-interviews")
    @PreAuthorize("hasRole('INTERVIEWER')")
    public ResponseEntity<List<InterviewResponseDto>> getMyInterviews(Authentication authentication){
        List<InterviewResponseDto> interviews = interviewerService.getMyInterviews(authentication.getName());

        return ResponseEntity.ok(interviews);
    }

    @PatchMapping("/{interviewId}/feedback")
    @PreAuthorize("hasRole('INTERVIEWER')")
    public ResponseEntity<InterviewResponseDto> submitFeedback (@PathVariable Long interviewId,
                                                                @Valid @RequestBody InterviewFeedbackDto request,
                                                                Authentication authentication){
        InterviewResponseDto response = interviewerService.submitFeedback(interviewId, request, authentication.getName());

        return ResponseEntity.ok(response);
    }
}
