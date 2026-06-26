package com.company.repository;

import com.company.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByInterviewer_Email(String email);
    List<Interview> findByApplication_Id(Long applicationId);
}