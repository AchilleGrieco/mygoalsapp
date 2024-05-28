package com.example.MyGoalsApp.functions.controller;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import com.example.MyGoalsApp.authentication.repository.UserRepository;
import com.example.MyGoalsApp.functions.dto.GoalResponseDto;
import com.example.MyGoalsApp.functions.dto.GoalTemplateDto;
import com.example.MyGoalsApp.functions.dto.GoalTemplateResponseDto;
import com.example.MyGoalsApp.functions.model.GoalTemplate;
import com.example.MyGoalsApp.functions.service.GoalTemplateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/goalTemplates")
public class GoalTemplateController {

    @Autowired
    GoalTemplateService goalTemplateService;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Object> getGoalTemplates(Authentication authentication) {

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        List<GoalTemplate> goalTemplates = goalTemplateService.getGoalTemplates(user);

        List<GoalTemplateResponseDto> goalTemplateResponseDtos = new ArrayList<>();
        for (GoalTemplate goalTemplate : goalTemplates) {
            GoalTemplateResponseDto goalTemplateResponseDto = new GoalTemplateResponseDto(
                    goalTemplate.getGoalId(),
                    goalTemplate.getName(),
                    goalTemplate.getIcon(),
                    user.getUserId()
            );
            goalTemplateResponseDtos.add(goalTemplateResponseDto);
        }

        goalTemplateResponseDtos = goalTemplateResponseDtos.stream()
                .sorted(Comparator.comparing(GoalTemplateResponseDto::getGoalId))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(goalTemplateResponseDtos);
    }

    @PostMapping
    public ResponseEntity<Object> addGoalTemplate(@RequestBody GoalTemplateDto goalTemplateDto, Authentication authentication) throws Exception {

        if (goalTemplateDto.getName() == null || goalTemplateDto.getName().isEmpty()) {
            return new ResponseEntity<>("The goal name can't be null or empty", HttpStatus.BAD_REQUEST);
        }

        if (goalTemplateDto.getIcon() == null || goalTemplateDto.getIcon().isEmpty()) {
            return new ResponseEntity<>("The goal icon can't be null or empty", HttpStatus.BAD_REQUEST);
        }

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        GoalTemplate goalTemplate = goalTemplateService.addGoalTemplate(goalTemplateDto, user);

        GoalTemplateResponseDto goalTemplateResponseDto = new GoalTemplateResponseDto(
                goalTemplate.getGoalId(),
                goalTemplate.getName(),
                goalTemplate.getIcon(),
                user.getUserId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(goalTemplateResponseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoalTemplate(@PathVariable Long id, Authentication authentication) {

        if (id == null) {
            return new ResponseEntity<>("The goal id can't be null", HttpStatus.BAD_REQUEST);
        }

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        try {
            goalTemplateService.deleteGoalTemplate(id, user);
        } catch (EntityNotFoundException e ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> modifyGoalTemplate(@PathVariable Long id, @RequestBody GoalTemplateDto goalTemplateDto, Authentication authentication) {

        if (id == null) {
            return new ResponseEntity<>("The goal id can't be null", HttpStatus.BAD_REQUEST);
        }

        String username = authentication.getName();
        if (userRepository.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserEntity user = userRepository.findByUsername(username).get();

        try {
            GoalTemplate goalTemplate = goalTemplateService.modifyGoalTemplate(id, goalTemplateDto, user);

            GoalTemplateResponseDto goalTemplateResponseDto = new GoalTemplateResponseDto(
                    goalTemplate.getGoalId(),
                    goalTemplate.getName(),
                    goalTemplate.getIcon(),
                    user.getUserId()
            );

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(goalTemplateResponseDto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
