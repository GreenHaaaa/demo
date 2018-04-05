package com.example.arcface.reposity;

import com.example.arcface.domain.Message;
import com.example.arcface.domain.Task;
import com.example.arcface.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public interface MessageReposity extends JpaRepository<Message,Long> {
        List<Message> findAllByToUser(User user);
        List<Message> findAllByTask(Task task);
}
