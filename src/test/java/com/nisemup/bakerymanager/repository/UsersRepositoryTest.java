package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UsersRepositoryTest {

    @MockBean
    private UsersRepository usersRepository;

    static final Long id = 1L;

    @Test
    void findByEmail_WhenEmailExists_ReturnOptionalWithUsers() {
        String email = "test@example.com";
        Users user = new Users();
        user.setEmail(email);
        when(usersRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        Optional<Users> result = usersRepository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user.getEmail(), result.get().getEmail());
        verify(usersRepository).findByEmail(email);
    }

    @Test
    void findByEmail_WhenEmailDoesNotExist_ReturnEmptyOptional() {
        String email = "nonexisting@example.com";
        when(usersRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        Optional<Users> result = usersRepository.findByEmail(email);

        assertTrue(result.isEmpty());
        verify(usersRepository).findByEmail(email);
    }

    @Test
    void findByUserId_WhenIdExists_ReturnOptionalWithUsers() {
        Users user = new Users();
        user.setUserId(id);
        when(usersRepository.findByUserId(id))
                .thenReturn(Optional.of(user));

        Optional<Users> result = usersRepository.findByUserId(id);

        assertTrue(result.isPresent());
        assertEquals(user.getUserId(), result.get().getUserId());
        verify(usersRepository).findByUserId(id);
    }

    @Test
    void findByUserId_WhenIdDoesNotExist_ReturnEmptyOptional() {
        when(usersRepository.findByUserId(id))
                .thenReturn(Optional.empty());

        Optional<Users> result = usersRepository.findByUserId(id);

        assertTrue(result.isEmpty());
        verify(usersRepository).findByUserId(id);
    }

    @Test
    void deleteById_WhenIdExists_MustDeleteUser() {
        Users user = new Users();
        user.setUserId(id);
        when(usersRepository.findByUserId(id))
                .thenReturn(Optional.of(user));

        usersRepository.deleteById(id);

        verify(usersRepository).deleteById(id);
    }
}