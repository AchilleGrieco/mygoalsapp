package com.example.MyGoalsApp.functions.service;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import com.example.MyGoalsApp.functions.dto.GoalTemplateDto;
import com.example.MyGoalsApp.functions.exceptions.ForbiddenException;
import com.example.MyGoalsApp.functions.model.Goal;
import com.example.MyGoalsApp.functions.model.GoalTemplate;
import com.example.MyGoalsApp.functions.repository.GoalTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoalTemplateService {

    @Autowired
    GoalTemplateRepository goalTemplateRepository;

    public List<GoalTemplate> getGoalTemplates(UserEntity user) {
       return goalTemplateRepository.findAllByUser(user);
    }

    public GoalTemplate addGoalTemplate(GoalTemplateDto goalTemplateDto, UserEntity user) {
        GoalTemplate goalTemplate = new GoalTemplate(goalTemplateDto.getName(), goalTemplateDto.getIcon(), user);
        goalTemplateRepository.saveAndFlush(goalTemplate);
        return goalTemplate;
    }

    public void deleteGoalTemplate(Long goalId, UserEntity user) {
        Optional<GoalTemplate> optionalGoalTemplate = goalTemplateRepository.findById(goalId);

        if (optionalGoalTemplate.isEmpty()) {
            throw new EntityNotFoundException("Goal template not found");
        }
        if (!optionalGoalTemplate.get().getUser().getUsername().equals(user.getUsername())) {
            throw new ForbiddenException("Forbidden");
        }

        goalTemplateRepository.delete(optionalGoalTemplate.get());
    }

    public GoalTemplate modifyGoalTemplate(Long id, GoalTemplateDto goalTemplateDto, UserEntity user) {
        Optional<GoalTemplate> optionalGoalTemplate = goalTemplateRepository.findById(id);

        if (optionalGoalTemplate.isEmpty()) {
            throw new EntityNotFoundException("Goal template not found");
        }
        if (!optionalGoalTemplate.get().getUser().getUsername().equals(user.getUsername())) {
            throw new ForbiddenException("Forbidden");
        }

        GoalTemplate goalTemplate = optionalGoalTemplate.get();

        if (goalTemplateDto.getName() != null && !goalTemplateDto.getName().isEmpty()) {
            goalTemplate.setName(goalTemplateDto.getName());
        }
        if (goalTemplateDto.getIcon() != null && !goalTemplateDto.getIcon().isEmpty()) {
            goalTemplate.setIcon(goalTemplateDto.getIcon());
        }

        goalTemplateRepository.saveAndFlush(goalTemplate);
        return goalTemplate;
    }

}
