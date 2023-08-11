package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.PaymentMethod;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WithMockUser
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = "/sql/create-paymentMethod-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create-paymentMethod-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PaymentMethodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Long id = 1L;

    @Test
    void getPaymentMethods_ShouldReturnPaymentMethodsPage() throws Exception {
        mockMvc.perform(get("/panel/payments"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/payments/payments"))
                .andExpect(model().attributeExists("payments"))
                .andExpect(content().string(containsString("Test payment 1")))
                .andExpect(content().string(containsString("Test payment 2")));
    }

    @Test
    void getPaymentMethodInfo_WithValidPaymentMethod_ShouldReturnPaymentMethodInfoPage() throws Exception {
        mockMvc.perform(get("/panel/payments/{paymentMethod}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/payments/info"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attribute("payment", hasProperty("type", is("Test payment 1"))))
                .andExpect(model().attribute("payment", hasProperty("allowed", is(true))));
    }

    @Test
    void newPaymentMethod_ShouldReturnNewPaymentMethodPage() throws Exception {
        mockMvc.perform(get("/panel/payments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/payments/new"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attribute("payment", isA(PaymentMethod.class)));
    }

    @Test
    void createPaymentMethod_WithValidPaymentMethod_ShouldRedirectToPaymentMethodsPage() throws Exception {
        mockMvc.perform(post("/panel/payments")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("type", "New type")
                        .param("allowed", "false")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/payments"));
    }

    @Test
    void editPaymentMethodInfo_WithValidPaymentMethod_ShouldReturnEditPaymentMethodPage() throws Exception {
        mockMvc.perform(get("/panel/payments/{id}/edit", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/payments/edit"))
                .andExpect(model().attributeExists("payment"))
                .andExpect(model().attribute("payment", hasProperty("type", is("Test payment 1"))))
                .andExpect(model().attribute("payment", hasProperty("allowed", is(true))));
    }

    @Test
    void updatePaymentMethod_WithValidPaymentMethod_ShouldRedirectToPaymentMethodsPage() throws Exception {
        mockMvc.perform(patch("/panel/payments/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("type", "Updated")
                        .param("allowed", "false")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/payments"));
    }

    @Test
    void deletePaymentMethod_WithValidPaymentMethod_ShouldRedirectToPaymentMethodsPage() throws Exception {
        mockMvc.perform(delete("/panel/payments/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/payments"));
    }
}