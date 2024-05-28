package com.example.MyGoalsApp.functions.dto;

import lombok.Data;

@Data
public class GoalResponseDto {

    private Long goalId;

    private String name;

    private String icon;

    private String frequency;

    private Long userId;

    public GoalResponseDto(Long goalId, String name, String icon, String frequency, Long userId) {
        this.goalId = goalId;
        this.name = name;
        this.icon = icon;
        this.frequency = frequency;
        this.userId = userId;
    }
}
