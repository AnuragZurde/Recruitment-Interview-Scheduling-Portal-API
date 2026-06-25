package com.company.mapper;

import com.company.dto.applicationDto.ApplicationResponseDto;
import com.company.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "applicationId", source = "id")
    @Mapping(target = "candidateName" , source = "candidate.fullName")
    @Mapping(target = "candidateEmail", source = "candidate.email")
    @Mapping(target = "jobTitle", source = "jobPosting.title")
    ApplicationResponseDto toDto(Application application);
}
