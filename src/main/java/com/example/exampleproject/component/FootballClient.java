package com.example.exampleproject.component;

import com.example.exampleproject.annotation.TimeMeasure;
import com.example.exampleproject.exception.MissedParameterException;
import com.example.exampleproject.exception.UpdateException;
import com.example.exampleproject.model.apidto.*;
import com.example.exampleproject.service.FootballSaveService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FootballClient {

    private final ObjectMapper mapper;
    private final FootballSaveService saveService;
    private final RestTemplate restTemplate;
    private final static String API_URL = "https://api-football-v1.p.rapidapi.com/v3/";

    @Autowired
    public FootballClient(ObjectMapper objectMapper, FootballSaveService saveService, @Value(value = "#{systemProperties['rapid.api.key']}") String rapidApiKey) {
        mapper = objectMapper;
        this.saveService = saveService;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        restTemplate = new RestTemplateBuilder()
                .defaultHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
                .defaultHeader("x-rapidapi-key", rapidApiKey)
                .build();
    }

    @TimeMeasure
    public List<LeagueWrapperDto> getLeague(String name, boolean save) throws JsonProcessingException {
        UriComponentsBuilder fromPath = UriComponentsBuilder.fromPath("leagues");
        if (name != null) {
            fromPath.queryParam("name", name);
        }
        LeagueWrapperDto[] leagueDtos = callGetMethod(fromPath.build().toString(), LeagueWrapperDto[].class);
        List<LeagueWrapperDto> dtos = List.of(leagueDtos);
        if (save) {
            dtos.forEach(saveService::saveLeague);
        }
        return dtos;
    }

    //example fixtures?season=2021&team=33&from=2021-11-15&to=2021-11-22"
    public List<MatchDto> getMatches(Integer apiTeamId, Integer apiLeagueId, Integer season, LocalDate date, boolean save, boolean fetchStatistics)
            throws JsonProcessingException, MissedParameterException {
        String fixtureUrl = getFixtureUrl(season, date, apiTeamId, apiLeagueId);
        MatchDto[] matchDtos = callGetMethod(fixtureUrl, MatchDto[].class);
        List<MatchDto> dtos = List.of(matchDtos);
        final Map<Long, List<StatisticsWrapperDto>> statHolderMap = new HashMap<>();
        if (fetchStatistics) {
            for (MatchDto wrapperDto : dtos) {
                Long fixtureId = wrapperDto.fixture().id();
                statHolderMap.put(fixtureId, getStatistics(fixtureId));
            }
        }
        if (save) {
            dtos.forEach(dto -> saveService.saveMatch(dto, statHolderMap.get(dto.fixture().id())));
        }

        return dtos;
    }

    public List<StatisticsWrapperDto> getStatistics(Long fixtureId) throws MissedParameterException, JsonProcessingException {
        UriComponentsBuilder fromPath = UriComponentsBuilder.fromPath("fixtures/statistics");
        if (fixtureId == null) {
            throw new MissedParameterException("Parameter fixtureId is missing");
        }
        String fixtureUrl = fromPath.queryParam("fixture", fixtureId).build().toString();
        StatisticsWrapperDto[] statisticsWrapperDtos = callGetMethod(fixtureUrl, StatisticsWrapperDto[].class);
        return List.of(statisticsWrapperDtos);
    }

    public List<MatchDto> updateMatch(Integer apiTeamId, Integer apiLeagueId, Integer season, LocalDate date) throws JsonProcessingException, MissedParameterException, UpdateException {
        MatchDto[] matchDtos = callGetMethod(getFixtureUrl(season, date, apiTeamId, apiLeagueId), MatchDto[].class);
        List<MatchDto> dtos = List.of(matchDtos);
        if (dtos.isEmpty()) {
            throw new UpdateException("Update Exception");
        }
        final Map<Long, List<StatisticsWrapperDto>> statHolderMap = new HashMap<>();
        for (MatchDto wrapperDto : dtos) {
            Long fixtureId = wrapperDto.fixture().id();
            statHolderMap.put(fixtureId, getStatistics(fixtureId));
        }
        for (MatchDto dto : dtos) {
            saveService.updateMatch(dto, statHolderMap.get(dto.fixture().id()));
        }
        return dtos;
    }

    public List<TeamWrapper> getTeams(String name, String countryName, boolean save) throws JsonProcessingException {
        UriComponentsBuilder fromPath = UriComponentsBuilder.fromPath("teams");
        if (name != null) {
            fromPath.queryParam("name", name);
        }
        if (countryName != null) {
            fromPath.queryParam("country", countryName);
        }
        TeamDtoWrapper[] teamDtos = callGetMethod(fromPath.build().toString(), TeamDtoWrapper[].class);
        List<TeamWrapper> dtoWrappers = List.of(teamDtos);
        if (save) {
            dtoWrappers.forEach(saveService::saveTeam);
        }
        return dtoWrappers;
    }


    private String getFixtureUrl(Integer season, LocalDate date, Integer apiTeamId, Integer apiLeagueId) {
        UriComponentsBuilder fromPath = UriComponentsBuilder.fromPath("fixtures");
        if (season != null) {
            fromPath.queryParam("season", season);
        }
        if (date != null) {
            fromPath.queryParam("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (apiTeamId != null) {
            fromPath.queryParam("team", apiTeamId);
        }
        if (apiLeagueId != null) {
            fromPath.queryParam("league", apiLeagueId);
        }

        return fromPath.build().toString();
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) throws JsonProcessingException {
        String json = restTemplate.getForObject(API_URL + url, String.class, objects);
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode response = jsonNode.get("response");
        return mapper.treeToValue(response, responseType);
    }

}
