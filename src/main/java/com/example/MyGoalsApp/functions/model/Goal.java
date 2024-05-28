package com.example.MyGoalsApp.functions.model;

import com.example.MyGoalsApp.authentication.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    private String name;

    private String icon;

    private String frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Goal(String name, String icon, String frequency, UserEntity user) {
        this.name = name;
        this.icon = icon;
        this.frequency = frequency;
        this.user = user;
    }

    public Goal() {}
}
