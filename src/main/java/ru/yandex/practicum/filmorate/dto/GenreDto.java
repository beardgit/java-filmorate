package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class GenreDto {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("name")
    String name;
}