package com.example.exampleproject.model.apidto;

import javax.validation.constraints.NotNull;

public record LeagueDto(@NotNull Long id,@NotNull String name, String type, String logo,CountryDto countryDto) implements ILeague{
}
