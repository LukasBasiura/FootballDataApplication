package com.example.exampleproject.model.apidto;

import java.util.List;

public record StatisticsWrapperDto(Team team, List<Statistics> statistics) {



    public record Statistics(String type, String value){}
    public record Team(String name,String logo){}
}
