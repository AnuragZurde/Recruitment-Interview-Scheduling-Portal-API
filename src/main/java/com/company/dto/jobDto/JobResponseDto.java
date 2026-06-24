package com.company.dto.jobDto;

import com.company.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDto {

    private Long id;
    private String title;
    private String description;
    private double minSalary;
    private double maxSalary;
    private String hrAdminName;
    private Set<String> skills;
}
