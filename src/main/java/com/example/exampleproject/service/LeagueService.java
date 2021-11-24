package com.example.exampleproject.service;

import com.example.exampleproject.exception.AlreadyExistingException;
import com.example.exampleproject.exception.NoSuchCountryException;
import com.example.exampleproject.model.apidto.ILeague;
import com.example.exampleproject.model.entity.Country;
import com.example.exampleproject.model.entity.League;
import com.example.exampleproject.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final CountryService countryService;

    private League findByName(League league) {
        return leagueRepository.findByName(league.getName());
    }

    public League saveLeague(ILeague league, Country country) {
        League byName = findLeagueByNameAndCountry(league.name(), country);
        if (byName != null) {
            return byName;
        }
        League leagueEntity = new League();
        leagueEntity.setName(league.name());
        leagueEntity.setLogo(league.logo());
        leagueEntity.setType(league.type());
        leagueEntity.setCountry(country);
        return leagueRepository.save(leagueEntity);
    }

    public League saveLeague(ILeague league, Long countryId) {
        var byName = findLeaguesByNameAndCountryId(league.name(), countryId);
        if (!byName.getSecond().isEmpty()) {
            throw new AlreadyExistingException("League already exists : " + league);
        }
        League leagueEntity = new League();
        leagueEntity.setName(league.name());
        leagueEntity.setLogo(league.logo());
        leagueEntity.setType(league.type());
        leagueEntity.setCountry(byName.getFirst());
        return leagueRepository.save(leagueEntity);
    }

    private Pair<Country, List<League>> findLeaguesByNameAndCountryId(String name, Long countryId) {
        Country dbCountry = countryService.findCountryById(countryId);
        if (dbCountry == null) {
            throw new NoSuchCountryException("Couldn't find country by id : " + countryId);
        }
        return Pair.of(dbCountry, leagueRepository.findLeaguesByNameAndCountry(name, dbCountry));
    }

    public League findLeagueByNameAndCountry(String name, String country) {
        Country countryByName = countryService.findCountryByName(country);
        return leagueRepository.findLeagueByNameAndCountry_Id(name, countryByName.getId());
    }

    public League findLeagueByNameAndCountry(String name, Country country) {
        return findLeagueByNameAndCountry(name, country.getName());
    }

    public League findLeagueById(Long id) {
        return leagueRepository.findById(id).orElse(null);
    }

    public List<League> findLeagueByCountryName(String countryName) {
        Country countryByName = countryService.findCountryByName(countryName);
        return leagueRepository.findLeaguesByCountry(countryByName);
    }

    public List<League> findLeaguesByName(String name) {
        return leagueRepository.findLeaguesByName(name);
    }

    public List<League> findLeaguesByNameAndCountryName(String name, String countryName) {
        Country dbCountry = countryService.findCountryByName(countryName);
        return leagueRepository.findLeaguesByNameAndCountry(name, dbCountry);
    }

    public void deleteLeague(Long id) {
        leagueRepository.deleteById(id);
    }
}
