package com.company.controller;

import com.company.dto.applicationDto.ApplicationRequestDto;
import com.company.dto.applicationDto.ApplicationResponseDto;
import com.company.reppository.ApplicationRepository;
import com.company.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/job/{jobId}")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<String > applyForJob(@PathVariable Long jobId,
                                               @Valid @RequestBody ApplicationRequestDto request,
                                               Authentication authentication){
        String response = applicationService.applyForJob(jobId, request, authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my-application")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<List<ApplicationResponseDto>> getMyApplications(Authentication authentication){
        List<ApplicationResponseDto> applications = applicationService.getMyApplications(authentication.getName());

        return ResponseEntity.ok(applications);
    }
}
