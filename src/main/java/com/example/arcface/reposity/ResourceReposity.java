package com.example.arcface.reposity;

import com.example.arcface.domain.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceReposity extends JpaRepository<Resources,Integer>{
}
