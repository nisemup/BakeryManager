package com.nisemup.bakerymanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    protected static final Long id = 1L;
}