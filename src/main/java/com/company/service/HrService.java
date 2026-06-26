package com.company.service;

import com.company.dto.jobDto.JobRequestDto;
import com.company.dto.jobDto.JobResponseDto;
import com.company.entity.JobPosting;
import com.company.entity.Skill;
import com.company.entity.User;
import com.company.mapper.JobMapper;
import com.company.repository.JobPostingRepository;
import com.company.repository.SkillRepository;
import com.company.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HrService {

    private final JobPostingRepository jobPostingRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final JobMapper jobMapper;

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

    @Transactional
    public List<JobResponseDto> getAllActiveJobs () {
        List<JobPosting> activeJobs = jobPostingRepository.findByIsActiveTrue();

        return activeJobs.stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public String deleteJob (Long jobId, String hrEmail) throws AccessDeniedException {
        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(()-> new IllegalArgumentException("Job Not Found."));

        if (!jobPosting.getAdmin().getEmail().equals(hrEmail)){
            throw new AccessDeniedException("You Do not have permission to edit this job details.");
        }
        jobPosting.setIsActive(false);
        jobPostingRepository.save(jobPosting);

        return "Job Details Successfully";
    }

    @Transactional
    public String updateJobDetails (Long jobId, @Valid JobRequestDto request, String hrEmail) throws Exception{
        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(()-> new IllegalArgumentException("Job Not Found."));

        if (!jobPosting.getAdmin().getEmail().equals(hrEmail)){
            throw new AccessDeniedException("You Do not have permission to edit this job details.");
        }

        Set<Skill> updateSkills = new HashSet<>();
        for (String skillName : request.getSkills()){
            String formattedName = skillName.trim() .toUpperCase();
            Skill skill =  skillRepository.findByName(formattedName)
                    .orElseGet(()-> skillRepository.save(Skill.builder().name(formattedName).build()));
            updateSkills.add(skill);
        }

        jobPosting.setTitle(request.getTitle());
        jobPosting.setDescription(request.getDescription());
        jobPosting.setMinSalary(request.getMinSalary());
        jobPosting.setMaxSalary(request.getMaxSalary());
        jobPosting.setRequiredSkills(updateSkills);

        jobPostingRepository.save(jobPosting);
        return "Job Updated Successfully";
    }

    @Transactional
    public JobResponseDto patchJobDetails (Long jobId, @Valid Map<String, Object> updates, String hrEmail) throws Exception{
        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(()-> new IllegalArgumentException("Job Not Found."));

        if (!jobPosting.getAdmin().getEmail().equals(hrEmail)){
            throw new AccessDeniedException("You Do not have permission to edit this job details.");
        }

        updates.forEach((key, data)->{
            switch (key){
                case "title" -> jobPosting.setTitle((String) data);
                case "description" -> jobPosting.setDescription((String) data);
                case "minSalary" -> jobPosting.setMinSalary(Double.valueOf(data.toString()));
                case "maxSalary" -> jobPosting.setMaxSalary(Double.valueOf(data.toString()));
                case "isActive" -> jobPosting.setIsActive((Boolean) data);

                case "skills" -> {
                    @SuppressWarnings("unchecked")
                    List<String> skillNames = (List<String>) data;
                    Set<Skill> updatedSkills = new HashSet<>();
                    for (String skillName : skillNames) {
                        String formattedName = skillName.trim().toUpperCase();
                        Skill skill = skillRepository.findByName(formattedName)
                                .orElseGet(() -> skillRepository.save(Skill.builder().name(formattedName).build()));
                        updatedSkills.add(skill);
                    }
                    jobPosting.setRequiredSkills(updatedSkills);
                }

                    default ->
                        throw new IllegalArgumentException("Field " + key + " is not updatable or does not exist.");
            }
        });

        JobPosting updatedJob = jobPostingRepository.save(jobPosting);

        return jobMapper.toDto(updatedJob);
    }
}