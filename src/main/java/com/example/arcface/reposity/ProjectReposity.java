package com.example.arcface.reposity;

import com.example.arcface.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectReposity extends JpaRepository<Project,String> {
}


