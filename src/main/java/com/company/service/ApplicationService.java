package com.company.service;

import com.company.dto.applicationDto.ApplicationRequestDto;
import com.company.dto.applicationDto.ApplicationResponseDto;
import com.company.entity.Application;
import com.company.entity.JobPosting;
import com.company.entity.User;
import com.company.enums.ApplicationStatus;
import com.company.mapper.ApplicationMapper;
import com.company.repository.ApplicationRepository;
import com.company.repository.JobPostingRepository;
import com.company.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;
    private final ApplicationMapper applicationMapper;

    @Transactional
    public String applyForJob (Long jobId, @Valid ApplicationRequestDto request, String candidateEmail) {

        User candidate = userRepository.findByEmail(candidateEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found."));

        JobPosting jobPosting =jobPostingRepository.findById(jobId)
                .orElseThrow(()-> new IllegalArgumentException("Job Not Found."));

        if (!jobPosting.getIsActive()){
            throw new IllegalStateException("This Job is no longer is active and cannot accept application.");
        }

        if (applicationRepository.existsByCandidate_IdAndJobPosting_Id(candidate.getId(), jobPosting.getId())){
            throw new IllegalStateException("You have Already applied for this job.");
        }

        Application application = Application.builder()
                .candidate(candidate)
                .jobPosting(jobPosting)
                .resumeUrl(request.getResumeUrl())
                .status(ApplicationStatus.APPLIED)
                .build();

        applicationRepository.save(application);
        return "Application Submitted successfully.";
    }

    @Transactional
    public List<ApplicationResponseDto> getMyApplications (String candidateEmail) {
        User candidate = userRepository.findByEmail(candidateEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found."));

        List<Application> applications = applicationRepository.findByCandidate_Id(candidate.getId());

        return applications.stream()
                .map(applicationMapper ::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ApplicationResponseDto> getApplicationsForJob (Long jobId, String hrEmail) throws Exception{
        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(()-> new IllegalArgumentException("Job Not Found."));

        if (!jobPosting.getAdmin().getEmail().equals(hrEmail)){
            throw new AccessDeniedException("You Don Not have Permission to view applications for this job.");
        }
        List<Application> applications = applicationRepository.findByJobPosting_Id(jobId);

        return applications.stream()
                .map(applicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicationResponseDto updateApplicationStatus (Long applicationId, ApplicationStatus status, String hrEmail) throws Exception{

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()-> new IllegalArgumentException("Application Not Found."));

        if (!application.getJobPosting().getAdmin().getEmail().equals(hrEmail)){
            throw  new AccessDeniedException("You Do not have permission to update this Application.");
        }

        application.setStatus(status);

        Application savedApplication = applicationRepository.save(application);

        return applicationMapper.toDto(savedApplication);
    }
}
