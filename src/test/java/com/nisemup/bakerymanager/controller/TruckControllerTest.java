package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Truck;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Sql(value = "/sql/create-trucks-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create-trucks-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TruckControllerTest extends AbstractControllerTest {

    @Test
    void getTrucks_ShouldReturnTrucksPage() throws Exception {
        mockMvc.perform(get("/panel/trucks"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/trucks/trucks"))
                .andExpect(model().attributeExists("trucks"))
                .andExpect(content().string(containsString("Test brand 1 Test model 1 (Test plate 1)")))
                .andExpect(content().string(containsString("Test brand 2 Test model 2 (Test plate 2)")));
    }

    @Test
    void getTruckInfo_WithValidTruck_ShouldReturnTruckInfoPage() throws Exception {
        mockMvc.perform(get("/panel/trucks/{truck}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/trucks/info"))
                .andExpect(model().attributeExists("truck"))
                .andExpect(model().attribute("truck", hasProperty("brand", is("Test brand 1"))))
                .andExpect(model().attribute("truck", hasProperty("model", is("Test model 1"))))
                .andExpect(model().attribute("truck", hasProperty("color", is("Test color 1"))))
                .andExpect(model().attribute("truck", hasProperty("numberPlate", is("Test plate 1"))));
    }

    @Test
    void newTruck_ShouldReturnNewTruckPage() throws Exception {
        mockMvc.perform(get("/panel/trucks/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/trucks/new"))
                .andExpect(model().attributeExists("truck"))
                .andExpect(model().attribute("truck", isA(Truck.class)));
    }

    @Test
    void createTruck_WithValidTruck_ShouldRedirectToTrucksPage() throws Exception {
        mockMvc.perform(post("/panel/trucks")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("brand", "New Brand")
                        .param("model", "New Model")
                        .param("color", "New color")
                        .param("numberPlate", "New plate")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/trucks"));
    }

    @Test
    void editTruckInfo_WithValidTruck_ShouldReturnEditTruckPage() throws Exception {
        mockMvc.perform(get("/panel/trucks/{id}/edit", id))
                .andExpect(status().isOk())
                .andExpect(view().name("/panel/trucks/edit"))
                .andExpect(model().attributeExists("truck"))
                .andExpect(model().attribute("truck", hasProperty("brand", is("Test brand 1"))))
                .andExpect(model().attribute("truck", hasProperty("model", is("Test model 1"))))
                .andExpect(model().attribute("truck", hasProperty("color", is("Test color 1"))))
                .andExpect(model().attribute("truck", hasProperty("numberPlate", is("Test plate 1"))));
    }

    @Test
    void updateTruck_WithValidTruck_ShouldRedirectToTrucksPage() throws Exception {
        mockMvc.perform(patch("/panel/trucks/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("brand", "Updated")
                        .param("model", "Updated")
                        .param("color", "Updated")
                        .param("numberPlate", "Updated")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/trucks"));
    }

    @Test
    void deleteTruck_WithValidTruck_ShouldRedirectToTrucksPage() throws Exception {
        mockMvc.perform(delete("/panel/trucks/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/trucks"));
    }
}