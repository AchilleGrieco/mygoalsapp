package com.example.MyGoalsApp.functions.controller;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import com.example.MyGoalsApp.authentication.repository.UserRepository;
import com.example.MyGoalsApp.functions.dto.GoalDto;
import com.example.MyGoalsApp.functions.dto.GoalResponseDto;
import com.example.MyGoalsApp.functions.exceptions.ForbiddenException;
import com.example.MyGoalsApp.functions.model.Goal;
import com.example.MyGoalsApp.functions.service.GoalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    GoalService goalService;

    @Autowired
    UserRepository userRepository;


    @GetMapping
    public ResponseEntity<Object> getGoals(Authentication authentication) {

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        List<Goal> goals = goalService.getGoalsByUser(user);

        List<GoalResponseDto> goalResponseDtos = new ArrayList<>();
        for (Goal goal : goals) {
            GoalResponseDto goalResponseDto = new GoalResponseDto(
                    goal.getGoalId(),
                    goal.getName(),
                    goal.getIcon(),
                    goal.getFrequency(),
                    user.getUserId()
            );
            goalResponseDtos.add(goalResponseDto);
        }

        goalResponseDtos = goalResponseDtos.stream()
                .sorted(Comparator.comparing(GoalResponseDto::getGoalId))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(goalResponseDtos);
    }

    @PostMapping
    public ResponseEntity<Object> addGoal(@RequestBody GoalDto goalDto, Authentication authentication) {
        if (goalDto.getName() == null || goalDto.getName().isEmpty()) {
            return new ResponseEntity<>("Goal name can't be empty", HttpStatus.BAD_REQUEST);
        }
        if (goalDto.getIcon() == null || goalDto.getIcon().isEmpty()) {
            return new ResponseEntity<>("Goal icon can't be empty", HttpStatus.BAD_REQUEST);
        }
        if (goalDto.getFrequency() == null || goalDto.getFrequency().isEmpty()) {
            return new ResponseEntity<>("Goal frequency can't be empty", HttpStatus.BAD_REQUEST);
        }

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        Goal goal = goalService.addGoal(goalDto, user);

        GoalResponseDto goalResponseDto = new GoalResponseDto(
                goal.getGoalId(),
                goal.getName(),
                goal.getIcon(),
                goal.getFrequency(),
                user.getUserId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(goalResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable Long id, Authentication authentication) {
        if (id == null) {
            return new ResponseEntity<>("Goal id can't be null", HttpStatus.BAD_REQUEST);
        }

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        try {
            goalService.deleteGoal(id, user);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> modifyGoal(@PathVariable Long id, @RequestBody GoalDto goalDto, Authentication authentication) {

        if (id == null) {
            return new ResponseEntity<>("The goal id can't be null", HttpStatus.BAD_REQUEST);
        }

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        try {
            Goal goal = goalService.modifyGoal(id, goalDto, user);

            GoalResponseDto goalResponseDto = new GoalResponseDto(
                    goal.getGoalId(),
                    goal.getName(),
                    goal.getIcon(),
                    goal.getFrequency(),
                    user.getUserId()
            );

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(goalResponseDto);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
