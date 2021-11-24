package com.example.exampleproject.model.apidto;

public record TeamDto(long id, int founded, String name, String logo, boolean national, String country) implements ITeam{

}
