package com.example.exampleproject.model.apidto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public record LeagueWrapperDto(@JsonProperty("league") LeagueDto league, @JsonProperty("country") CountryDto country, @JsonProperty("seasons") List<SeasonDto> seasons) implements ILeagueDto {

    @Override
    public List<? extends ISeason> getSeasons() {
        return seasons;
    }
}
