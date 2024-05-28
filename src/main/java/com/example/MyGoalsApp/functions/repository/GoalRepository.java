package com.example.MyGoalsApp.functions.repository;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import com.example.MyGoalsApp.functions.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findAllByUser(UserEntity user);
}
