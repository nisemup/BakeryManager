package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Users;
import com.nisemup.bakerymanager.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    // TODO: Make exception
    public Optional<Users> findById(Long id) {
        return usersRepository.findByUserId(id);
    }

    // TODO: Make exception
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    public boolean create(Users user) {
        Optional<Users> userAdd = usersRepository.findByEmail(user.getEmail());

        if (userAdd.isPresent()) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersRepository.save(user);

        return true;
    }

    public void update(Long id, Users user) {
        if (usersRepository.findByUserId(id).isPresent()) {
            // TODO: Make exception
            Users updatableUser = usersRepository.findByUserId(id).get();

            updatableUser.setAvatar(user.getAvatar());
            updatableUser.setEmail(user.getEmail());
            updatableUser.setBirthday(user.getBirthday());
            updatableUser.setFirstName(user.getFirstName());
            updatableUser.setLastName(user.getLastName());
            updatableUser.setRole(user.getRole());
            updatableUser.setStatus(user.getStatus());

            usersRepository.save(updatableUser);
        }
    }
}
