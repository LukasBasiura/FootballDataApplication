package com.example.exampleproject.service;

import com.example.exampleproject.model.apidto.ITeam;
import com.example.exampleproject.model.entity.Country;
import com.example.exampleproject.model.entity.Team;
import com.example.exampleproject.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TeamService {
    private final TeamRepository teamRepository;
    private final CountryService countryService;

    public Team saveTeam(ITeam teamDto, String countryName) {
        Country countryByName = countryService.findCountryByName(countryName);
        if (countryByName == null) {
            log.error("Country not found for : " + teamDto);
            return null;
        }
        Team teamByNameAndCountry = teamRepository.findTeamByNameAndCountry(teamDto.name(), countryByName);
        if (teamByNameAndCountry == null) {
            Team teamEntity = new Team();
            teamEntity.setName(teamDto.name());
            teamEntity.setNational(teamDto.national());
            teamEntity.setLogo(teamDto.logo());
            teamEntity.setCountry(countryByName);
            return teamRepository.save(teamEntity);
        }
        return teamByNameAndCountry;
    }

    public Team findTeamByName(String name) {
        return teamRepository.findTeamByName(name);
    }

    public Team findTeamById(Long homeTeamId) {
        return teamRepository.findById(homeTeamId).orElse(null);
    }
}
