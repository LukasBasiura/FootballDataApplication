package com.example.exampleproject.controller;

import com.example.exampleproject.component.FootballClient;
import com.example.exampleproject.exception.MissedParameterException;
import com.example.exampleproject.exception.UpdateException;
import com.example.exampleproject.model.apidto.LeagueWrapperDto;
import com.example.exampleproject.model.apidto.MatchDto;
import com.example.exampleproject.model.apidto.TeamWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/football")
@RequiredArgsConstructor
public class FootApiController {

    private final FootballClient footballClient;

    @GetMapping(value = "/leagues", produces = "application/json")
    public List<LeagueWrapperDto> getLeagues(@RequestParam(required = false) String name,
                                             @RequestParam(required = false, defaultValue = "false") boolean save)
            throws JsonProcessingException {
        return footballClient.getLeague(name, save);
    }

    @GetMapping(value = "/teams", produces = "application/json")
    public List<TeamWrapper> getTeams(@RequestParam(required = false) String teamName,
                                      @RequestParam(required = false, defaultValue = "false") boolean save,
                                      @RequestParam(required = false) String countryName)
            throws JsonProcessingException {
        if (teamName == null && countryName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one argument must be set apart from save");
        }
        return footballClient.getTeams(teamName, countryName, save);
    }


    @GetMapping(value = "/matches", produces = "application/json")
    public List<MatchDto> getMatches(@RequestParam(required = false) Integer apiTeamId,
                                     @RequestParam(required = false) Integer apiLeagueId,
                                     @RequestParam(required = false) Integer season,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     @RequestParam(required = false, defaultValue = "false") boolean save,
                                     @RequestParam(required = false) boolean fetchStatistics)
            throws JsonProcessingException, MissedParameterException {
        if (apiTeamId == null && apiLeagueId == null && season == null && date == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one argument must be set apart from save");
        }
        return footballClient.getMatches(apiTeamId, apiLeagueId,season,date, save,fetchStatistics);
    }

    @PutMapping(value = "/matches", produces = "application/json")
    public List<MatchDto> updateMatches(@RequestParam(required = false) Integer apiTeamId,
                                        @RequestParam(required = false) Integer apiLeagueId,
                                        @RequestParam(required = false) Integer season,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
            throws JsonProcessingException, MissedParameterException, UpdateException {
        if (apiTeamId == null && apiLeagueId == null && season == null && date == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one argument must be set apart from save");
        }
        return footballClient.updateMatch(apiTeamId, apiLeagueId,season,date);
    }

}
