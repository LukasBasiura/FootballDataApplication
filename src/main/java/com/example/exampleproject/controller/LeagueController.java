package com.example.exampleproject.controller;

import com.example.exampleproject.model.apidto.CountryDto;
import com.example.exampleproject.model.apidto.LeagueDto;
import com.example.exampleproject.model.entity.Country;
import com.example.exampleproject.model.entity.League;
import com.example.exampleproject.service.LeagueService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;
    private final ModelMapper modelMapper;


    @GetMapping("/leagues")
    public List<LeagueDto> getLeagues(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String countryName) {
        if (name == null && countryName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one argument must be filled");
        }
        if (name == null) {
            List<League> dbLeagues = leagueService.findLeagueByCountryName(countryName);
            return convertLeaguesToDtoList(dbLeagues);
        }
        if (countryName == null) {
            List<League> dbLeagues = leagueService.findLeaguesByName(name);
            return convertLeaguesToDtoList(dbLeagues);
        }
        List<League> dbLeagues = leagueService.findLeaguesByNameAndCountryName(name, countryName);
        return convertLeaguesToDtoList(dbLeagues);
    }

    @GetMapping("/leagues/{id}")
    public LeagueDto getLeagues(@Positive @PathVariable Long id) {
        League leagueById = leagueService.findLeagueById(id);
        LeagueDto leagueDto = new LeagueDto(leagueById.getId(), leagueById.getName(), leagueById.getType(), leagueById.getLogo(),modelMapper.map(leagueById.getCountry(),CountryDto.class));
        return leagueDto;
    }

    @PostMapping(value = "/leagues", consumes = "application/json")
    public LeagueDto postLeague(@Valid @NotNull @RequestBody LeagueDto leagueDto, @Positive @NotNull @RequestParam Long countryId) {
        League saveLeague = leagueService.saveLeague(leagueDto, countryId);
        Country country = saveLeague.getCountry();
        return new LeagueDto(saveLeague.getId(), saveLeague.getName(), saveLeague.getType(), saveLeague.getLogo(), new CountryDto(country.getId(), country.getName(), country.getCode(), country.getFlag()));
    }

    @DeleteMapping("/leagues/{id}")
    public void deleteLeague(@Positive @PathVariable Long id) {
        leagueService.deleteLeague(id);
    }

    private List<LeagueDto> convertLeaguesToDtoList(List<League> dbLeagues) {
        return dbLeagues.stream().map(db -> new LeagueDto(db.getId(), db.getName(), db.getType(), db.getLogo(), modelMapper.map(db.getCountry(), CountryDto.class))).collect(Collectors.toList());
    }
}
