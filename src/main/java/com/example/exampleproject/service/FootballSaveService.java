package com.example.exampleproject.service;

import com.example.exampleproject.exception.UpdateException;
import com.example.exampleproject.model.StatisticDtoToEntity;
import com.example.exampleproject.model.apidto.*;
import com.example.exampleproject.model.entity.*;
import com.example.exampleproject.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Log4j2
public class FootballSaveService {

    private final CountryService countryService;
    private final SeasonService seasonService;
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final MatchService matchService;
    private final StatisticsRepository statisticsRepository;

    public void saveLeague(ILeagueDto leagueDto) {
        ICountry country = leagueDto.country();
        Country dbCountry = countryService.saveCountry(country);

        ILeague league = leagueDto.league();
        League dbLeague = leagueService.saveLeague(league, dbCountry);

        var seasons = leagueDto.getSeasons();
        for (ISeason iSeason : seasons) {
            seasonService.saveSeason(iSeason, dbLeague);
        }
    }


    public Team saveTeam(TeamWrapper teamWrapper) {
        ITeam teamDto = teamWrapper.teamDto();
        String country = teamDto.country();
        return teamService.saveTeam(teamDto, country);
    }

    public Match saveMatch(MatchDto matchDto, List<StatisticsWrapperDto> statistics) {
        MatchDto.League league = matchDto.league();
        League dbLeague = leagueService.findLeagueByNameAndCountry(league.name(), league.country());

        if (dbLeague == null) {
            log.error("League not found for : " + matchDto);
            return null;
        }

        MatchDto.Teams teams = matchDto.teams();
        MatchDto.Team homeTeamDto = teams.home();
        MatchDto.Team awayTeamDto = teams.away();

        Team dbHomeTeam = teamService.findTeamByName(homeTeamDto.name());
        Team dbAwayTeam = teamService.findTeamByName(awayTeamDto.name());

        if (dbHomeTeam == null || dbAwayTeam == null) {
            log.error("Couldn't find one of a teams in a match : " + matchDto);
            return null;
        }

        MatchDto.Score score = matchDto.score();
        MatchDto.FullTime fulltime = score.fulltime();
        MatchDto.HalfTime halftime = score.halftime();
        MatchDto.Penalty penalty = score.penalty();
        MatchDto.ExtraTime extraTime = score.extratime();

        Match.FullTimeScore fullTimeScore = new Match.FullTimeScore(fulltime.home(), fulltime.away());
        Match.HalfTimeScore halfTimeScore = new Match.HalfTimeScore(halftime.home(), halftime.away());
        Match.ExtraTimeScore extraTimeScore = new Match.ExtraTimeScore(extraTime.home(), extraTime.away());
        Match.Penalty penalty1 = new Match.Penalty(penalty.home(), penalty.away());

        MatchDto.Fixture fixture = matchDto.fixture();

        AtomicReference<Statistic> homeStat = new AtomicReference<>(null);
        AtomicReference<Statistic> awayStat = new AtomicReference<>(null);

        setStatistics(statistics,homeTeamDto.name(),awayTeamDto.name(),homeStat,awayStat);

        return matchService.saveMatch(dbLeague, dbHomeTeam, dbAwayTeam, fullTimeScore,
                halfTimeScore, extraTimeScore, penalty1, LocalDate.parse(fixture.date()),
                homeStat.get(), awayStat.get());
    }

    @Transactional
    public void updateMatch(MatchDto dto, List<StatisticsWrapperDto> statistics) throws UpdateException {
        String homeName = dto.teams().home().name();
        String awayName = dto.teams().away().name();
        Match dbMatch = matchService.findMatchByHomeAwayDate(homeName, awayName, LocalDate.parse(dto.fixture().date()));
        if (dbMatch == null) {
            throw new UpdateException("Cannot update match because it does not exist");
        }
        var homeStat = new AtomicReference<Statistic>(null);
        var awayStat = new AtomicReference<Statistic>(null);

        setStatistics(statistics,homeName,awayName,homeStat,awayStat);

        dbMatch.setHomeStat(homeStat.get());
        dbMatch.setAwayStat(awayStat.get());
    }

    private void setStatistics(List<StatisticsWrapperDto> statistics, String homeName, String awayName,
                               AtomicReference<Statistic> homeStat, AtomicReference<Statistic> awayStat) {
        if (statistics != null) {
            statistics.stream().filter(swd -> swd.team().name().equals(homeName)).findFirst().ifPresent(hStat -> {
                StatisticDtoToEntity homeDtoToEntity = new StatisticDtoToEntity(hStat.statistics());
                homeStat.set(homeDtoToEntity.getEntityInstance());
            });
            statistics.stream().filter(swd -> swd.team().name().equals(awayName)).findFirst().ifPresent(aStat -> {
                StatisticDtoToEntity awayDtoToEntity = new StatisticDtoToEntity(aStat.statistics());
                awayStat.set(awayDtoToEntity.getEntityInstance());
            });
        }
    }
}
