package com.company.repository;

import com.company.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidate_Id(Long candidateId);

    List<Application> findByJobPosting_Id(Long jobId);

    boolean existsByCandidate_IdAndJobPosting_Id(Long candidateId, Long jobId);
}