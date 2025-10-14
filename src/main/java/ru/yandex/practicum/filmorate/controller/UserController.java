package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserServiceImplementation;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImplementation userServiceImplementation;

    @Autowired
    public UserController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @GetMapping
    public Collection<User> findAllUsers() {
        return userServiceImplementation.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
        return userServiceImplementation.findUserById(id);
    }

    @PostMapping
    public User appendUser(@Valid @RequestBody User user) {
        return userServiceImplementation.create(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userServiceImplementation.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userServiceImplementation.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        userServiceImplementation.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable long id) {
        return userServiceImplementation.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(
            @PathVariable long id,
            @PathVariable long otherId) {
        return userServiceImplementation.getCommonFriends(id, otherId);
    }
}

