package com.company.controller;

import com.company.dto.jobDto.JobRequestDto;
import com.company.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('HR_ADMIN')")
    public ResponseEntity<String > createJob(@Valid @RequestBody JobRequestDto request, Authentication authentication){

        String hrEmail = authentication.getName();

        String response = jobService.createJob(request, hrEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
