package com.company.controller;

import com.company.dto.InterviewDto.InterviewRequestDto;
import com.company.dto.applicationDto.ApplicationResponseDto;
import com.company.dto.jobDto.JobRequestDto;
import com.company.dto.jobDto.JobResponseDto;
import com.company.enums.ApplicationStatus;
import com.company.service.ApplicationService;
import com.company.service.HrService;
import com.company.service.InterviewerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hr/job")
public class HrController {

    private final HrService hrService;

    private final ApplicationService applicationService;

    private final InterviewerService interviewerService;

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String > createJob(@Valid @RequestBody JobRequestDto request, Authentication authentication){

        String hrEmail = authentication.getName();

        String response = hrService.createJob(request, hrEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobResponseDto>> getAllActiveJobs(){
        List<JobResponseDto> jobs = hrService.getAllActiveJobs();
        return ResponseEntity.ok(jobs);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId, Authentication authentication) throws AccessDeniedException {

        String response = hrService.deleteJob(jobId, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String> updateJob(@PathVariable Long jobId, @Valid @RequestBody JobRequestDto request,
                                            Authentication authentication) throws Exception {
        String response = hrService.updateJobDetails(jobId, request, authentication.getName());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{jobId}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<JobResponseDto> patchJobDetails(@PathVariable Long jobId, @Valid @RequestBody Map<String , Object> updates, Authentication authentication) throws Exception {

        JobResponseDto jobResponseDto = hrService.patchJobDetails(jobId, updates, authentication.getName());

        return ResponseEntity.ok(jobResponseDto);
    }

    @GetMapping("/{jobId}/applications")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<List<ApplicationResponseDto>> getApplicationForJob(@PathVariable Long jobId,
                                                                             Authentication authentication) throws Exception {
        List<ApplicationResponseDto> applications = applicationService.getApplicationsForJob(jobId, authentication.getName());
        return ResponseEntity.ok(applications);
    }

    @PatchMapping("/applications/{applicationId}/status")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<ApplicationResponseDto> updateApplicationStatus(@PathVariable Long applicationId,
                                                                          @RequestParam ApplicationStatus status,
                                                                          Authentication authentication) throws Exception{
        ApplicationResponseDto updateApplication = applicationService.updateApplicationStatus(applicationId, status, authentication.getName());

        return ResponseEntity.ok(updateApplication);
    }

    @PostMapping("/application/{applicationId}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String> scheduleInterview(@PathVariable Long applicationId,
                                                    @Valid @RequestBody InterviewRequestDto request,
                                                    Authentication authentication){
        String response = interviewerService.scheduleInterview(applicationId, request, authentication.getName());

        return ResponseEntity.ok(response);
    }
}
