package com.example.arcface.reposity;

import com.example.arcface.domain.Project;
import com.example.arcface.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskReposity extends JpaRepository<Task,Long> {
    List<Task> findAllByProject(Project project);
    List<Task> findAllBySecurityLvLessThanEqual(int lv);
}
