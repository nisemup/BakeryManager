package com.nisemup.bakerymanager.service;

import com.nisemup.bakerymanager.model.Role;
import com.nisemup.bakerymanager.model.Status;
import com.nisemup.bakerymanager.model.Users;
import com.nisemup.bakerymanager.repository.UsersRepository;
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
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    private static final Long id = 1L;

    @Test
    void findAll_WhenUsersExists_ReturnListOptionalWithUsers() {
        List<Users> usersList = Arrays.asList(new Users(), new Users());
        when(usersRepository.findAll())
                .thenReturn(usersList);

        List<Users> result = usersService.findAll();

        assertEquals(usersList, result);
    }

    @Test
    void findById_WhenIdExist_ReturnOptionalWithUser() {
        Optional<Users> userOptional = Optional.of(new Users());
        when(usersRepository.findByUserId(id))
                .thenReturn(userOptional);

        Optional<Users> result = usersService.findById(id);

        assertEquals(userOptional, result);
    }

    @Test
    void deleteById_WhenIdExist_MustDeleteUser() {
        usersService.deleteById(id);

        verify(usersRepository).deleteById(id);
    }

    @Test
    void create_WithNewUser_MustCreateUser() {
        Users user = new Users();
        when(usersRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        boolean result = usersService.create(user);

        verify(passwordEncoder).encode(user.getPassword());
        verify(usersRepository).save(user);
        assertTrue(result);
    }

    @Test
    void create_WithExistingUser_MustNotCreateUser() {
        Users existingUser = new Users();
        when(usersRepository.findByEmail(existingUser.getEmail()))
                .thenReturn(Optional.of(existingUser));

        boolean result = usersService.create(existingUser);

        verify(passwordEncoder, never()).encode(existingUser.getPassword());
        verify(usersRepository, never()).save(existingUser);
        assertFalse(result);
    }

    @Test
    void update_WhenUserExist_MustUpdateUser() {
        Users existingUser = new Users();
        Users newUser = new Users();
        newUser.setAvatar("avatar.png");
        newUser.setEmail("email@example.com");
        newUser.setBirthday(LocalDate.now());
        newUser.setFirstName("Test");
        newUser.setLastName("Tesst");
        newUser.setRole(Role.USER);
        newUser.setStatus(Status.ACTIVE);


        when(usersRepository.findByUserId(id))
                .thenReturn(Optional.of(existingUser));

        usersService.update(id, newUser);

        verify(usersRepository).save(existingUser);
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
        Users newUser = new Users();

        when(usersRepository.findByUserId(id))
                .thenReturn(Optional.empty());

        usersService.update(id, newUser);

        verify(usersRepository, never()).save(any(Users.class));
    }
}