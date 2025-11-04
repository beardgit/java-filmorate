package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
public class FilmResponseDto {
    @JsonProperty("id")
    long id;
    @JsonProperty("name")
    String name;
    @JsonProperty("description")
    String description;
    @JsonProperty("releaseDate")
    LocalDate releaseDate;
    @JsonProperty("duration")
    int duration;
    @JsonProperty("mpa")
    MpaDto mpa;
    @JsonProperty("genres")
    Set<GenreDto> genres;
}