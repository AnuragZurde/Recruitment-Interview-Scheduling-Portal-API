package com.company.service;

import com.company.dto.jobDto.JobRequestDto;
import com.company.entity.JobPosting;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.reppository.JobPostingRepository;
import com.company.reppository.SkillRepository;
import com.company.reppository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobPostingRepository jobPostingRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public String createJob (@Valid JobRequestDto request, String hrEmail) {

        User heAdmin = userRepository.findByEmail(hrEmail).orElseThrow(() -> new UsernameNotFoundException("HR Admin not Found"));

        Set<Skill> jobSkills = new HashSet<>();
        for (String skillName : request.getSkills()){
            String formattedName = skillName.trim().toUpperCase();

            Skill skill = skillRepository.findByName(formattedName)
                    .orElseGet(()-> {
                        Skill newSkill = Skill.builder().name(formattedName).build();
                        return skillRepository.save(newSkill);
                    });

            jobSkills.add(skill);
        }

        JobPosting jobPosting = JobPosting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .minSalary(request.getMinSalary())
                .maxSalary(request.getMaxSalary())
                .isActive(true)
                .admin(heAdmin)
                .requiredSkills(jobSkills)
                .build();

        jobPostingRepository.save(jobPosting);

        return "Job Created Successfully";
    }
}
