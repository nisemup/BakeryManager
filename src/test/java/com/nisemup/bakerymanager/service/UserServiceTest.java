package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Role;
import com.nisemup.bakerymanager.model.Status;
import com.nisemup.bakerymanager.model.User;
import com.nisemup.bakerymanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static final Long id = 1L;

    @Test
    void findAll_WhenUsersExists_ReturnListOptionalWithUsers() {
        List<User> userList = Arrays.asList(new User(), new User());
        when(userRepository.findAll())
                .thenReturn(userList);

        List<User> result = userService.findAll();

        assertEquals(userList, result);
    }

    @Test
    void findById_WhenIdExist_ReturnOptionalWithUser() {
        Optional<User> userOptional = Optional.of(new User());
        when(userRepository.findById(id))
                .thenReturn(userOptional);

        Optional<User> result = userService.findById(id);

        assertEquals(userOptional, result);
    }

    @Test
    void deleteById_WhenIdExist_MustDeleteUser() {
        userService.deleteById(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void create_WithNewUser_MustCreateUser() {
        User user = new User();
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        userService.create(user);

        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void create_WithExistingUser_MustNotCreateUser() {
        User existingUser = new User();
        when(userRepository.findByEmail(existingUser.getEmail()))
                .thenReturn(Optional.of(existingUser));

        userService.create(existingUser);

        verify(passwordEncoder, never()).encode(existingUser.getPassword());
        verify(userRepository, never()).save(existingUser);
    }

    @Test
    void update_WhenUserExist_MustUpdateUser() {
        User existingUser = new User();
        User newUser = new User();
        newUser.setAvatar("avatar.png");
        newUser.setEmail("email@example.com");
        newUser.setBirthday(LocalDate.now());
        newUser.setFirstName("Test");
        newUser.setLastName("Tesst");
        newUser.setRole(Role.USER);
        newUser.setStatus(Status.ACTIVE);


        when(userRepository.findById(id))
                .thenReturn(Optional.of(existingUser));

        userService.update(id, newUser);

        verify(userRepository).save(existingUser);
        assertEquals(newUser.getAvatar(), existingUser.getAvatar());
        assertEquals(newUser.getEmail(), existingUser.getEmail());
        assertEquals(newUser.getBirthday(), existingUser.getBirthday());
        assertEquals(newUser.getFirstName(), existingUser.getFirstName());
        assertEquals(newUser.getLastName(), existingUser.getLastName());
        assertEquals(newUser.getRole(), existingUser.getRole());
        assertEquals(newUser.getStatus(), existingUser.getStatus());
    }

    @Test
    void update_WhenUserDoesNotExist_MustNotUpdateUser() {
        User newUser = new User();

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        userService.update(id, newUser);

        verify(userRepository, never()).save(any(User.class));
    }
}