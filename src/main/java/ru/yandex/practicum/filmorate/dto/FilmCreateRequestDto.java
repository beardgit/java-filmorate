package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.DateValidator;

import java.time.LocalDate;
import java.util.Set;

@Data
public class FilmCreateRequestDto {
    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    @JsonProperty("name")
    String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    @JsonProperty("description")
    String description;

    @DateValidator
    @JsonProperty("releaseDate")
    LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    @JsonProperty("duration")
    Integer duration;

    @JsonProperty("mpa")
    MpaDto mpa;
    @JsonProperty("genres")
    Set<GenreDto> genres;
}
