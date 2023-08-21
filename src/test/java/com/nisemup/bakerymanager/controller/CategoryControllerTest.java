package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(value = "/sql/create-categories-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create-categories-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CategoryControllerTest extends AbstractControllerTest {

    @Test
    void getCategory_ShouldReturnCategoriesPage() throws Exception {
        mockMvc.perform(get("/panel/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/categories/categories"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(content().string(containsString("Test Name 1")))
                .andExpect(content().string(containsString("Test Name 2")));
    }

    @Test
    void getCategoryInfo_WithValidCategory_ShouldReturnCategoryInfoPage() throws Exception {
        mockMvc.perform(get("/panel/categories/{category}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/categories/info"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("category", hasProperty("name", is("Test Name 1"))))
                .andExpect(model().attribute("category", hasProperty("description", is("Test Description 1"))));
    }

    @Test
    void newCategory_ShouldReturnNewCategoryPage() throws Exception {
        mockMvc.perform(get("/panel/categories/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/categories/new"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("category", isA(Category.class)));
    }

    @Test
    void createCategory_WithValidCategory_ShouldRedirectToCategoriesPage() throws Exception {
        mockMvc.perform(post("/panel/categories")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("name", "New Name")
                        .param("description", "New Description")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/categories"));
    }

    @Test
    void editCategoryInfo_WithValidCategory_ShouldReturnEditCategoryPage() throws Exception {
        mockMvc.perform(get("/panel/categories/{id}/edit", id))
                .andExpect(status().isOk())
                .andExpect(view().name("/panel/categories/edit"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attribute("category", hasProperty("name", is("Test Name 1"))))
                .andExpect(model().attribute("category", hasProperty("description", is("Test Description 1"))));
    }

    @Test
    void updateCategory_WithValidCategory_ShouldRedirectToCategoriesPage() throws Exception {
        mockMvc.perform(patch("/panel/categories/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("name", "Updated")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/categories"));
    }

    @Test
    void deleteCategory_WithValidCategory_ShouldRedirectToCategoriesPage() throws Exception {
        mockMvc.perform(delete("/panel/categories/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/categories"));
    }
}