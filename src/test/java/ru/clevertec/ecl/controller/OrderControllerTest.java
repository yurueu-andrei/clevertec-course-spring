package ru.clevertec.ecl.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.utils.TestUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseControllerTest {

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
                .withDatabaseName("testPostgres")
                .withUsername("postgres")
                .withPassword("postgres")
                .withReuse(true);

        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void datasourceConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void findByIdTest_shouldReturnOrderWithId2() throws Exception {
        //given
        Order order = TestUtils.getOrder(2);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/2"))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.createDate").isNotEmpty())
                .andExpect(jsonPath("$.cost").value(order.getCost()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void findAllTest_shouldReturnOrdersWithIdFrom6To10() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders?page=1&size=5"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(6))
                .andExpect(jsonPath("$[0].createDate").isNotEmpty())
                .andExpect(jsonPath("$[1].id").value(7))
                .andExpect(jsonPath("$[1].createDate").isNotEmpty())
                .andExpect(jsonPath("$[2].id").value(8))
                .andExpect(jsonPath("$[2].createDate").isNotEmpty())
                .andExpect(jsonPath("$[3].id").value(9))
                .andExpect(jsonPath("$[3].createDate").isNotEmpty())
                .andExpect(jsonPath("$[4].id").value(10))
                .andExpect(jsonPath("$[4].createDate").isNotEmpty())
                .andExpect(jsonPath("$[5]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}