package com.example.MyGoalsApp.functions.repository;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import com.example.MyGoalsApp.functions.model.GoalTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalTemplateRepository extends JpaRepository<GoalTemplate, Long> {

    List<GoalTemplate> findAllByUser(UserEntity user);

}
