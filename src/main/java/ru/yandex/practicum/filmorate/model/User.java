package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;

    @NotBlank(message = "электронная почта не может быть пустой")
    @Email(message = "должна содержать символ @")
    private String email;

    @NotBlank(message = "логин не может быть пустым и содержать пробелы")
    private String login;

    private String name;

    @PastOrPresent(message = "дата рождения не может быть в будущем")
    private LocalDate birthday;

    public String getName() {
        if (this.name == null || this.name.isBlank()) {
            return this.login;
        }
        return this.name;
    }
}