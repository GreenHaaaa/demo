package com.example.arcface.reposity;

import com.example.arcface.domain.Authority;
import com.example.arcface.domain.Project;
import com.example.arcface.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityReposity extends JpaRepository<Authority,String>{
    List<Authority> findAllByUser(User user);
    List<Authority> findAllByProject(Project project);
    Authority findAllByUserAndProject(User user,Project project);

}
