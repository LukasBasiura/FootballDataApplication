package com.example.exampleproject.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public final class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String shotsOnGoal;
    private String shotsOffGoal;
    private String totalShots;
    private String blockedShots;
    private String shotsInsideBox;
    private String shotsOutsideBox;
    private String fouls;
    private String cornerKicks;
    private String offsides;
    private String ballPossession;
    private String yellowCards;
    private String redCards;
    private String goalkeeperSaves;
    private String totalPasses;
    private String passesAccurate;


}
