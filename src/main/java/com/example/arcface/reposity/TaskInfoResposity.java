package com.example.arcface.reposity;

import com.example.arcface.domain.TaskUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskInfoResposity  extends JpaRepository<TaskUserInfo,Long>{

    TaskUserInfo getAllByUserIdAndTaskId(String userId,Long taskId);
    List<TaskUserInfo> findAllByTaskId(Long taskid);
}
