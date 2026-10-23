package com.example.practika300.service;

import com.example.practika300.model.User;
import com.example.practika300.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Регистрация пользователя. @return true — если пользователь создан, false — если логин уже занят.

    public boolean register(String username, String password) {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return false;
        }
        if (userRepository.findByUsername(username).isPresent()) {
            return false;
        }
        userRepository.save(new User(username, password));
        return true;
    }

    //Проверка логина и пароля. @return true — если учётные данные верны.
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.isPresent() && userOpt.get().getPassword().equals(password);
    }
}
