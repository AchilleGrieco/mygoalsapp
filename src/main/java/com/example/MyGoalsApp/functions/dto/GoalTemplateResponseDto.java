package com.example.MyGoalsApp.functions.dto;

import lombok.Data;

@Data
public class GoalTemplateResponseDto {

    private Long goalId;

    private String name;

    private String icon;

    private Long userId;

    public GoalTemplateResponseDto(Long goalId, String name, String icon, Long userId) {
        this.goalId = goalId;
        this.name = name;
        this.icon = icon;
        this.userId = userId;
    }
}
