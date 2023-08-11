package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.exception.NoEntityException;
import com.nisemup.bakerymanager.model.User;
import com.nisemup.bakerymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void update(Long id, User user) {
        User updatableUser = userRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("Customer not found!"));

        updatableUser.setAvatar(user.getAvatar());
        updatableUser.setEmail(user.getEmail());
        updatableUser.setBirthday(user.getBirthday());
        updatableUser.setFirstName(user.getFirstName());
        updatableUser.setLastName(user.getLastName());
        updatableUser.setRole(user.getRole());
        updatableUser.setStatus(user.getStatus());

        userRepository.save(updatableUser);
    }
}
