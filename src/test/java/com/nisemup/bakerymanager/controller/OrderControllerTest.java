package com.nisemup.bakerymanager.controller;

import com.nisemup.bakerymanager.model.Order;
import com.nisemup.bakerymanager.model.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Sql(value = "/sql/create-orders-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/create-orders-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class OrderControllerTest extends AbstractControllerTest {

    @Test
    void getOrders_ShouldReturnOrdersPage() throws Exception {
        mockMvc.perform(get("/panel/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/orders/orders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("2")));
    }

    @Test
    void getOrderInfo_WithValidOrder_ShouldReturnOrderInfoPage() throws Exception {
        mockMvc.perform(get("/panel/orders/{order}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/orders/info"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("order", hasProperty("userId",
                        hasProperty("firstName", is("Name1")))))
                .andExpect(model().attribute("order", hasProperty("userId",
                        hasProperty("lastName", is("LastName1")))))
                .andExpect(model().attribute("order", hasProperty("paymentId",
                        hasProperty("type", is("Test payment 1")))))
                .andExpect(model().attribute("order",
                        hasProperty("orderDate", is(LocalDateTime.of(testDate, LocalTime.of(12, 30))))))
                .andExpect(model().attribute("order",
                        hasProperty("requiredDate", is(testDate))))
                .andExpect(model().attribute("order",
                        hasProperty("deliveredDate")))
                .andExpect(model().attribute("order",
                        hasProperty("transactionStatus", is(TransactionStatus.INPROGRESS))))
                .andExpect(model().attribute("order",
                        hasProperty("delivered", is(false))));
    }

    @Test
    void newOrder_ShouldReturnNewOrderPage() throws Exception {
        mockMvc.perform(get("/panel/orders/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("panel/orders/new"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("order", isA(Order.class)));
    }

    @Test
    void createOrder_WithValidOrder_ShouldRedirectToOrdersPage() throws Exception {
        mockMvc.perform(post("/panel/orders")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("userId", "1")
                        .param("customerId", "1")
                        .param("paymentId", "1")
                        .param("requiredDate", testDate.toString())
                        .param("deliveredDate", "2021-01-01T12:35")
                        .param("transactionStatus", "INPROGRESS")
                        .param("delivered", "true")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/orders"));
    }

    @Test
    void editOrderInfo_WithValidOrder_ShouldReturnEditOrderPage() throws Exception {
        mockMvc.perform(get("/panel/orders/{id}/edit", id))
                .andExpect(status().isOk())
                .andExpect(view().name("/panel/orders/edit"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("order", hasProperty("userId",
                        hasProperty("firstName", is("Name1")))))
                .andExpect(model().attribute("order", hasProperty("userId",
                        hasProperty("lastName", is("LastName1")))))
                .andExpect(model().attribute("order", hasProperty("paymentId",
                        hasProperty("type", is("Test payment 1")))))
                .andExpect(model().attribute("order", hasProperty("requiredDate", is(testDate))))
                .andExpect(model().attribute("order", hasProperty("deliveredDate")))
                .andExpect(model().attribute("order", hasProperty("transactionStatus", is(TransactionStatus.INPROGRESS))))
                .andExpect(model().attribute("order", hasProperty("delivered", is(false))));
    }

    @Test
    void updateOrder_WithValidOrder_ShouldRedirectToOrdersPage() throws Exception {
        mockMvc.perform(patch("/panel/orders/{id}", id)
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("user", "Name2 LastName2")
                        .param("customer", "Test Address 2")
                        .param("payment", "Test payment 2")
                        .param("requiredDate", testDate.plusDays(1).toString())
                        .param("deliveredDate", "2021-01-02T12:44")
                        .param("transactionStatus", "CANCELED")
                        .param("delivered", "false")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/orders"));
    }

    @Test
    void deleteOrder_WithValidOrder_ShouldRedirectToOrdersPage() throws Exception {
        mockMvc.perform(delete("/panel/orders/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/panel/orders"));
    }
}