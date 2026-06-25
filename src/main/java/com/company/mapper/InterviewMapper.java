package com.company.mapper;

import com.company.dto.InterviewDto.InterviewResponseDto;
import com.company.entity.Interview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InterviewMapper {

    @Mapping(target = "interviewId", source = "id")
    @Mapping(target = "candidateName", source = "application.candidate.fullName")
    @Mapping(target = "jobTitle", source = "application.jobPosting.title")
    @Mapping(target = "interviewerName", source = "interviewer.fullName")
    InterviewResponseDto toDto(Interview interview);
}
