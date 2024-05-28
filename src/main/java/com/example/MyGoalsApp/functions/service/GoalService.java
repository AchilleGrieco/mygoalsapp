package com.example.MyGoalsApp.functions.service;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import com.example.MyGoalsApp.functions.dto.GoalDto;
import com.example.MyGoalsApp.functions.exceptions.ForbiddenException;
import com.example.MyGoalsApp.functions.model.Goal;
import com.example.MyGoalsApp.functions.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    GoalRepository goalRepository;

    public List<Goal> getGoalsByUser(UserEntity user) {
        return goalRepository.findAllByUser(user);
    }

    public Goal addGoal(GoalDto goalDto, UserEntity user) {
        Goal goal = new Goal(goalDto.getName(), goalDto.getIcon(), goalDto.getFrequency(), user);
        goalRepository.saveAndFlush(goal);
        return goal;
    }

    public void deleteGoal(Long goalId, UserEntity user) {
        Optional<Goal> optionalGoal = goalRepository.findById(goalId);

        if (optionalGoal.isEmpty()) {
            throw new EntityNotFoundException("Goal not found");
        }
        if (!optionalGoal.get().getUser().getUsername().equals(user.getUsername())) {
            throw new ForbiddenException("Forbidden");
        }
        goalRepository.delete(optionalGoal.get());
    }


    public Goal modifyGoal(Long id, GoalDto goalDto, UserEntity user) {

        Optional<Goal> optionalGoal = goalRepository.findById(id);

        if (optionalGoal.isEmpty()) {
            throw new EntityNotFoundException("Goal not found");
        }
        if (!optionalGoal.get().getUser().getUsername().equals(user.getUsername())) {
            throw new ForbiddenException("Forbidden");
        }

        Goal goal = optionalGoal.get();

        if (goalDto.getName() != null && !goalDto.getName().isEmpty()) {
            goal.setName(goalDto.getName());
        }
        if (goalDto.getIcon() != null && !goalDto.getIcon().isEmpty()) {
            goal.setIcon(goalDto.getIcon());
        }
        if (goalDto.getFrequency() != null && !goalDto.getFrequency().isEmpty()) {
            goal.setFrequency(goalDto.getFrequency());
        }

        goalRepository.saveAndFlush(goal);
        return goal;
    }
}
