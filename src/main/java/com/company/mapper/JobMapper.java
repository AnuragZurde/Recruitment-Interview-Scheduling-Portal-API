package com.company.mapper;

import com.company.dto.jobDto.JobResponseDto;
import com.company.entity.JobPosting;
import com.company.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(target = "hrAdminName", source = "admin.fullName")
    @Mapping(target = "skills", source = "requiredSkills", qualifiedByName = "mapSkillsToString")
    JobResponseDto toDto(JobPosting jobPosting);

    @Named("mapSkillsToString")
    default Set<String> mapSkillsToStrings(Set<Skill> skills){
        if (skills == null){
            return null;
        }
        return skills.stream()
                .map(Skill::getName)
                .collect(Collectors.toSet());
    }
}
