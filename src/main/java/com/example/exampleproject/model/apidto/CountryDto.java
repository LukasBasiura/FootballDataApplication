package com.example.exampleproject.model.apidto;

public record CountryDto(Long id, String name, String code, String flag) implements ICountry {
}
