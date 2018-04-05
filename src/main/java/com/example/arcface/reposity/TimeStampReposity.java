package com.example.arcface.reposity;

import com.example.arcface.domain.Project;
import com.example.arcface.domain.Task;
import com.example.arcface.domain.TimeStamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeStampReposity extends JpaRepository<TimeStamp,Long>{
    List<TimeStamp> findAllByTask(Task task);
//    List<TimeStamp> findAllByProject(Project project);
 }
