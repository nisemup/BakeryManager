package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(value = "/sql/create-customers-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create-customers-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CustomerControllerTest extends AbstractControllerTest {

    @Test
    void getCustomer_ShouldReturnCustomersPage() throws Exception {
        mockMvc.perform(get("/panel/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/customers/customers"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(content().string(containsString("Test Company 1")))
                .andExpect(content().string(containsString("Test Company 2")));
    }

    @Test
    void getCustomerInfo_WithValidCustomer_ShouldReturnCustomerInfoPage() throws Exception {
        mockMvc.perform(get("/panel/customers/{customer}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/customers/info"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", hasProperty("companyName", is("Test Company 1"))))
                .andExpect(model().attribute("customer", hasProperty("name", is("Test Name 1"))))
                .andExpect(model().attribute("customer", hasProperty("phoneNumber", is("95734434"))))
                .andExpect(model().attribute("customer", hasProperty("address", is("Test Address 1"))));
    }

    @Test
    void newCustomer_ShouldReturnNewCustomerPage() throws Exception {
        mockMvc.perform(get("/panel/customers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/customers/new"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", isA(Customer.class)));
    }

    @Test
    void createCustomer_WithValidCustomer_ShouldRedirectToCustomersPage() throws Exception {
        mockMvc.perform(post("/panel/customers")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("companyName", "New Company")
                        .param("name", "New Name")
                        .param("phoneNumber", "95451234")
                        .param("address", "New Address")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/customers"));
    }

    @Test
    void editCustomerInfo_WithValidCustomer_ShouldReturnEditCustomerPage() throws Exception {
        mockMvc.perform(get("/panel/customers/{id}/edit", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/customers/edit"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", hasProperty("companyName", is("Test Company 1"))))
                .andExpect(model().attribute("customer", hasProperty("name", is("Test Name 1"))))
                .andExpect(model().attribute("customer", hasProperty("phoneNumber", is("95734434"))))
                .andExpect(model().attribute("customer", hasProperty("address", is("Test Address 1"))));
    }

    @Test
    void updateCustomer_WithValidCustomer_ShouldRedirectToCustomersPage() throws Exception {
        mockMvc.perform(patch("/panel/customers/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("companyName", "Updated")
                        .param("name", "Updated")
                        .param("address", "Updated")
                        .param("phoneNumber", "95451234")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/customers"));
    }

    @Test
    void deleteCustomer_WithValidCustomer_ShouldRedirectToCustomersPage() throws Exception {
        mockMvc.perform(delete("/panel/customers/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/customers"));
    }
}