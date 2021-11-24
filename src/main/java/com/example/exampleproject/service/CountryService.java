package com.example.exampleproject.service;

import com.example.exampleproject.model.apidto.CountryDto;
import com.example.exampleproject.model.apidto.ICountry;
import com.example.exampleproject.model.entity.Country;
import com.example.exampleproject.model.entity.League;
import com.example.exampleproject.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    private Country findCountryByName(Country country) {
        return findCountryByName(country.getName());
    }

    public Country findCountryByName(String name) {
        return countryRepository.findFirstByName(name);
    }

    public Country saveCountry(ICountry country) {
        Country countryEntity = new Country();
        countryEntity.setName(country.name());
        countryEntity.setFlag(country.flag());
        countryEntity.setCode(country.code());
        Country dbCountry = findCountryByName(countryEntity);
        if (dbCountry == null) {
            dbCountry = countryRepository.save(countryEntity);
        }
        return dbCountry;
    }

    public List<Country> addCountries(List<Country> countries) {
        return countryRepository.saveAll(countries);
    }

    public CountryDto getCountryByName(String name) {
        Country dbCountry = findCountryByName(name);
        return new CountryDto(dbCountry.getId(),dbCountry.getName(), dbCountry.getCode(), dbCountry.getFlag());
    }

    public List<CountryDto> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(dbC -> new CountryDto(dbC.getId(),dbC.getName(), dbC.getCode(), dbC.getFlag()))
                .collect(Collectors.toList());
    }

    public Country findCountryById(Long countryId) {
        return countryRepository.findById(countryId).orElse(null);
    }
}
