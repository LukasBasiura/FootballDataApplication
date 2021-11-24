package com.example.exampleproject.controller;

import com.example.exampleproject.annotation.TimeMeasure;
import com.example.exampleproject.model.apidto.CountryDto;
import com.example.exampleproject.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping(value = "/countries",produces = "application/json")
    public List<CountryDto> getCountry(@RequestParam(required = false) String name) {
        if (name == null) {
            return countryService.getAllCountries();
        }
        return List.of(countryService.getCountryByName(name));
    }

}
