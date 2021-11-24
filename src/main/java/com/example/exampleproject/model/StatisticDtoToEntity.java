package com.example.exampleproject.model;

import com.example.exampleproject.model.apidto.StatisticsWrapperDto;
import com.example.exampleproject.model.entity.Statistic;

import java.util.List;

public class StatisticDtoToEntity {

    private final Statistic instance;

    public StatisticDtoToEntity(List<StatisticsWrapperDto.Statistics> statistics) {
        instance = createInstance(statistics);
    }

    private Statistic createInstance(List<StatisticsWrapperDto.Statistics> statistics) {
        Statistic result = new Statistic();
        for (StatisticsWrapperDto.Statistics stat : statistics) {
            switch (stat.type()) {
                case "Shots on Goal" -> result.setShotsOnGoal(stat.value());
                case "Shots off Goal" -> result.setShotsOffGoal(stat.value());
                case "Total Shots" -> result.setTotalShots(stat.value());
                case "Blocked Shots" -> result.setBlockedShots(stat.value());
                case "Shots insidebox" -> result.setShotsInsideBox(stat.value());
                case "Shots outsidebox" -> result.setShotsOutsideBox(stat.value());
                case "Fouls" -> result.setFouls(stat.value());
                case "Corner Kicks" -> result.setCornerKicks(stat.value());
                case "Offsides" -> result.setOffsides(stat.value());
                case "Ball Possession" -> result.setBallPossession(stat.value());
                case "Yellow Cards" -> result.setYellowCards(stat.value());
                case "Red Cards" -> result.setRedCards(stat.value());
                case "Goalkeeper Saves" -> result.setGoalkeeperSaves(stat.value());
                case "Total passes" -> result.setTotalPasses(stat.value());
                case "Passes accurate" -> result.setPassesAccurate(stat.value());
            }
        }
        return result;
    }

    public Statistic getEntityInstance() {
        return instance;
    }
}
