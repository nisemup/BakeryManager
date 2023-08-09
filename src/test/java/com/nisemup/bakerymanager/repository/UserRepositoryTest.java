package com.nisemup.bakerymanager.repository;

import com.nisemup.bakerymanager.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    static final Long id = 1L;

    @Test
    void findByEmail_WhenEmailExists_ReturnOptionalWithUsers() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user.getEmail(), result.get().getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void findByEmail_WhenEmailDoesNotExist_ReturnEmptyOptional() {
        String email = "nonexisting@example.com";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        Optional<User> result = userRepository.findByEmail(email);

        assertTrue(result.isEmpty());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void findById_WhenIdExists_ReturnOptionalWithUsers() {
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        verify(userRepository).findById(id);
    }

    @Test
    void findById_WhenIdDoesNotExist_ReturnEmptyOptional() {
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<User> result = userRepository.findById(id);

        assertTrue(result.isEmpty());
        verify(userRepository).findById(id);
    }
}