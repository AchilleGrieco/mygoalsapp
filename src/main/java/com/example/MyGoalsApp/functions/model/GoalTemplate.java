package com.example.MyGoalsApp.functions.model;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "goaltemplates")
public class GoalTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    private String name;

    private String icon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public GoalTemplate(String name, String icon, UserEntity user) {
        this.name = name;
        this.icon = icon;
        this.user = user;
    }

    public GoalTemplate() {}

}
