package com.example.exampleproject.service;

import com.example.exampleproject.model.entity.League;
import com.example.exampleproject.model.entity.Match;
import com.example.exampleproject.model.entity.Statistic;
import com.example.exampleproject.model.entity.Team;
import com.example.exampleproject.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MatchService {
    private final MatchRepository matchRepository;
    private final LeagueService leagueService;
    private final TeamService teamService;

    public Match saveMatch(League dbLeague, Team dbHomeTeam, Team dbAwayTeam,
                           Match.FullTimeScore fullTimeScore, Match.HalfTimeScore halfTimeScore,
                           Match.ExtraTimeScore extraTimeScore, Match.Penalty penalty, LocalDate date, Statistic homeStat, Statistic awayStat) {
        Match dbMatch = matchRepository.findMatchByLeagueAndHomeAndAwayAndDate(dbLeague, dbHomeTeam, dbAwayTeam, date);
        if (dbMatch != null) {
            return dbMatch;
        }
        Match matchEntity = new Match();
        matchEntity.setLeague(dbLeague);
        matchEntity.setHome(dbHomeTeam);
        matchEntity.setAway(dbAwayTeam);
        matchEntity.setFullTimeScore(fullTimeScore);
        matchEntity.setHalfTimeScore(halfTimeScore);
        matchEntity.setExtraTimeScore(extraTimeScore);
        matchEntity.setPenalty(penalty);
        matchEntity.setDate(date);
        matchEntity.setHomeStat(homeStat);
        matchEntity.setAwayStat(awayStat);
        return matchRepository.save(matchEntity);
    }

    public Match findMatchByHomeAwayDate(String homeName, String awayName, LocalDate date) {
        Team home = teamService.findTeamByName(homeName);
        Team away = teamService.findTeamByName(awayName);
        if (home == null || away == null) {
            log.error("Couldn't find one of the teams : home -> " + homeName + " away -> " + awayName);
        }
        return matchRepository.findMatchByHomeAndAwayAndDate(home, away, date);
    }

    public List<Match> findAll(int pageNumber) {
        return matchRepository.findAllBy(PageRequest.of(pageNumber,20, Sort.by(Sort.Direction.ASC,"id")));
    }

    public Match saveMatch(Long leagueId, Long homeTeamId, Long awayTeamId, Match.FullTimeScore fullTimeScore,
                           Match.HalfTimeScore halfTimeScore, Match.ExtraTimeScore extraTimeScore, Match.Penalty penalty, LocalDate date) {
        League dbLeague = leagueService.findLeagueById(leagueId);
        Team homeTeam = teamService.findTeamById(homeTeamId);
        Team awayTeam = teamService.findTeamById(awayTeamId);

        Match dbMatch = matchRepository.findMatchByHomeAndAwayAndDate(homeTeam, awayTeam, date);
        if (dbMatch != null) {
            return dbMatch;
        }
        Match matchEntity = new Match();
        matchEntity.setLeague(dbLeague);
        matchEntity.setHome(homeTeam);
        matchEntity.setAway(awayTeam);
        matchEntity.setFullTimeScore(fullTimeScore);
        matchEntity.setHalfTimeScore(halfTimeScore);
        matchEntity.setExtraTimeScore(extraTimeScore);
        matchEntity.setPenalty(penalty);
        matchEntity.setDate(date);
        return matchRepository.save(matchEntity);
    }
}
