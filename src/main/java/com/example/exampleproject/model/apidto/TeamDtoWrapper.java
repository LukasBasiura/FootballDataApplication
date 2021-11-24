package com.example.exampleproject.model.apidto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public record TeamDtoWrapper(@JsonProperty("team") TeamDto teamDto) implements TeamWrapper {
}
