package com.company.controller;

import com.company.dto.jobDto.JobRequestDto;
import com.company.dto.jobDto.JobResponseDto;
import com.company.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/job")
public class HrController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String > createJob(@Valid @RequestBody JobRequestDto request, Authentication authentication){

        String hrEmail = authentication.getName();

        String response = jobService.createJob(request, hrEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobResponseDto>> getAllActiveJobs(){
        List<JobResponseDto> jobs = jobService.getAllActiveJobs();
        return ResponseEntity.ok(jobs);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId, Authentication authentication) throws AccessDeniedException {

        String response = jobService.deleteJob(jobId, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String> updateJob(@PathVariable Long jobId, @Valid @RequestBody JobRequestDto request,
                                            Authentication authentication) throws Exception {
        String response = jobService.updateJobDetails(jobId, request, authentication.getName());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{jobId}")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<JobResponseDto> patchJobDetails(@PathVariable Long jobId, @Valid @RequestBody Map<String , Object> updates, Authentication authentication) throws Exception {

        JobResponseDto jobResponseDto = jobService.patchJobDetails(jobId, updates, authentication.getName());

        return ResponseEntity.ok(jobResponseDto);
    }
}
