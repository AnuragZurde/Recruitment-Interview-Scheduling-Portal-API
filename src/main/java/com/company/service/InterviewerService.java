package com.company.service;

import com.company.dto.InterviewDto.InterviewFeedbackDto;
import com.company.dto.InterviewDto.InterviewRequestDto;
import com.company.dto.InterviewDto.InterviewResponseDto;
import com.company.entity.Application;
import com.company.entity.Interview;
import com.company.entity.User;
import com.company.enums.ApplicationStatus;
import com.company.enums.UserRole;
import com.company.mapper.InterviewMapper;
import com.company.repository.ApplicationRepository;
import com.company.repository.InterviewRepository;
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
public class InterviewerService {

    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    private final InterviewRepository interviewRepository;

    private final InterviewMapper interviewMapper;

    @Transactional
    public List<InterviewResponseDto> getMyInterviews (String interviewerEmail) {
        List<Interview> interviews = interviewRepository.findByInterviewer_Email(interviewerEmail);

        return interviews.stream()
                .map(interviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public InterviewResponseDto submitFeedback (Long interviewId, @Valid InterviewFeedbackDto request, String interviewerEmail) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(()-> new IllegalArgumentException("Interview Not Found."));

        if (!interview.getInterviewer().getEmail().equals(interviewerEmail)){
            throw new AccessDeniedException("You are not assigned to this interview.");
        }

        interview.setInterviewerFeedback(request.getFeedBack());
        interview.setPassed(request.getPassed());

        Interview savedInterview = interviewRepository.save(interview);

        return interviewMapper.toDto(savedInterview);
    }

    @Transactional
    public String scheduleInterview (Long applicationId, @Valid InterviewRequestDto request, String hrEmail) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()-> new IllegalArgumentException("Application Not Found."));

        if (!application.getJobPosting().getAdmin().getEmail().equals(hrEmail)){
            throw new AccessDeniedException("You Do Not Have Permission to Schedule interview this applications.");
        }

        User interviewer = userRepository.findByEmail(request.getInterviewerEmail())
                .orElseThrow(()-> new UsernameNotFoundException("Interviewer Not Found."));

        if (!interviewer.getUserRole().equals(UserRole.INTERVIEWER)){
            throw new IllegalArgumentException("The assigned User is not INTERVIEWER.");
        }

        Interview interview = Interview.builder()
                .application(application)
                .interviewer(interviewer)
                .scheduleTime(request.getScheduleTime())
                .meetingLink(request.getMeetingLink())
                .build();

        application.setStatus(ApplicationStatus.INTERVIEWING);
        applicationRepository.save(application);
        interviewRepository.save(interview);

        return "Interview scheduled successfully and Application status updated to INTERVIEWING.";
    }
}
