package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = "/sql/create-products-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create-products-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Long id = 1L;

    @Test
    void getProducts_ShouldReturnProductsPage() throws Exception {
        mockMvc.perform(get("/panel/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/products/products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(content().string(containsString("Test Product 1")))
                .andExpect(content().string(containsString("Test Product 2")));
    }

    @Test
    void getProductInfo_WithValidProduct_ShouldReturnProductInfoPage() throws Exception {
        mockMvc.perform(get("/panel/products/{product}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/products/info"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("name", is("Test Product 1"))))
                .andExpect(model().attribute("product", hasProperty("description", is(""))))
                .andExpect(model().attribute("product", hasProperty("categoryId", hasProperty("name", is("Test Name 1")))))
                .andExpect(model().attribute("product", hasProperty("price", is(new BigDecimal("15.00")))))
                .andExpect(model().attribute("product", hasProperty("discount", is(new BigDecimal("0.00")))))
                .andExpect(model().attribute("product", hasProperty("discountAvailable", is(false))));
    }

    @Test
    void newProduct_ShouldReturnNewProductPage() throws Exception {
        mockMvc.perform(get("/panel/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/products/new"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", isA(Product.class)));
    }

    @Test
    void createProduct_WithValidProduct_ShouldRedirectToProductsPage() throws Exception {
        mockMvc.perform(post("/panel/products")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("categoryId", "1")
                        .param("name", "New Product")
                        .param("description", "New Product")
                        .param("price", "155.0")
                        .param("discount", "0.0")
                        .param("discountAvailable", "false")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/products"));
    }

    @Test
    void editProductInfo_WithValidProduct_ShouldReturnEditProductPage() throws Exception {
        mockMvc.perform(get("/panel/products/{id}/edit", id))
                .andExpect(status().isOk())
                .andExpect(view().name("/panel/products/edit"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("categoryId", hasProperty("name", is("Test Name 1")))))
                .andExpect(model().attribute("product", hasProperty("name", is("Test Product 1"))))
                .andExpect(model().attribute("product", hasProperty("description", is(""))))
                .andExpect(model().attribute("product", hasProperty("price", is(new BigDecimal("15.00")))))
                .andExpect(model().attribute("product", hasProperty("discount", is(new BigDecimal("0.00")))))
                .andExpect(model().attribute("product", hasProperty("discountAvailable", is(false))));
    }

    @Test
    void updateProduct_WithValidProduct_ShouldRedirectToProductsPage() throws Exception {
        mockMvc.perform(patch("/panel/products/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("categoryId", "1")
                        .param("name", "Updated")
                        .param("description", "Updated")
                        .param("price", "100")
                        .param("discount", "15")
                        .param("discountAvailable", "true")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/products"));
    }

    @Test
    void deleteProduct_WithValidProduct_ShouldRedirectToProductsPage() throws Exception {
        mockMvc.perform(delete("/panel/products/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/products"));
    }
}