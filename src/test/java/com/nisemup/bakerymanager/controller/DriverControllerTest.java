package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = "/sql/create-drivers-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create-drivers-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Long id = 1L;

    @Test
    void getDrivers_ShouldReturnDriversPage() throws Exception {
        mockMvc.perform(get("/panel/drivers"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/drivers/drivers"))
                .andExpect(model().attributeExists("drivers"))
                .andExpect(content().string(containsString("Name1 LastName1 (Test plate 1)")))
                .andExpect(content().string(containsString("Name2 LastName2 (Test plate 2)")));
    }

    @Test
    void getDriverInfo_WithValidDriver_ShouldReturnDriverInfoPage() throws Exception {
        mockMvc.perform(get("/panel/drivers/{driver}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/drivers/info"))
                .andExpect(model().attributeExists("driver"))
                .andExpect(model().attribute("driver", hasProperty("user",
                        hasProperty("firstName", is("Name1")))))
                .andExpect(model().attribute("driver", hasProperty("user",
                        hasProperty("lastName", is("LastName1")))))
                .andExpect(model().attribute("driver", hasProperty("truck",
                        hasProperty("brand", is("Test brand 1")))))
                .andExpect(model().attribute("driver", hasProperty("truck",
                        hasProperty("model", is("Test model 1")))))
                .andExpect(model().attribute("driver", hasProperty("truck",
                        hasProperty("numberPlate", is("Test plate 1")))))
                .andExpect(model().attribute("driver", hasProperty("license", is("Test license 1"))));
    }

    @Test
    void newDriver_ShouldReturnNewDriverPage() throws Exception {
        mockMvc.perform(get("/panel/drivers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/drivers/new"))
                .andExpect(model().attributeExists("driver"))
                .andExpect(model().attribute("driver", isA(Driver.class)));
    }

    @Test
    void createDriver_WithValidDriver_ShouldRedirectToDriversPage() throws Exception {
        mockMvc.perform(post("/panel/drivers")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("user", "3")
                        .param("truck", "3")
                        .param("license", "New license")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/drivers"));
    }

    @Test
    void editDriverInfo_WithValidDriver_ShouldReturnEditDriverPage() throws Exception {
        mockMvc.perform(get("/panel/drivers/{id}/edit", id))
                .andExpect(status().isOk())
                .andExpect(view().name("/panel/drivers/edit"))
                .andExpect(model().attributeExists("driver"))
                .andExpect(model().attribute("driver", hasProperty("user",
                        hasProperty("firstName", is("Name1")))))
                .andExpect(model().attribute("driver", hasProperty("user",
                        hasProperty("lastName", is("LastName1")))))
                .andExpect(model().attribute("driver", hasProperty("truck",
                        hasProperty("brand", is("Test brand 1")))))
                .andExpect(model().attribute("driver", hasProperty("truck",
                        hasProperty("model", is("Test model 1")))))
                .andExpect(model().attribute("driver", hasProperty("truck",
                        hasProperty("numberPlate", is("Test plate 1")))))
                .andExpect(model().attribute("driver", hasProperty("license", is("Test license 1"))));
    }

    @Test
    void updateDriver_WithValidDriver_ShouldRedirectToDriversPage() throws Exception {
        mockMvc.perform(patch("/panel/drivers/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("user", "1")
                        .param("track", "3")
                        .param("license", "Updated")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/drivers"));
    }

    @Test
    void deleteDriver_WithValidDriver_ShouldRedirectToDriversPage() throws Exception {
        mockMvc.perform(delete("/panel/drivers/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/drivers"));
    }
}