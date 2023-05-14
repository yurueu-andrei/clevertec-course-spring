package ru.clevertec.ecl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.ecl.dto.UserSaveDto;
import ru.clevertec.ecl.dto.UserUpdateDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.utils.TestUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

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
    private ObjectMapper mapper;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void findByIdTest_shouldReturnUserWithId2() throws Exception {
        //given
        User user = TestUtils.getUser(2);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/2"))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.orders").isNotEmpty())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void findAllTest_shouldReturnAllUsers() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users?page=1&size=2"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[1].id").value(4))
                .andExpect(jsonPath("$[2]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void addTest_shouldAddUser() throws Exception {
        //given
        UserSaveDto user = new UserSaveDto(
                "Yaroslav",
                "Parfenov",
                19,
                "parfenov228@mail.ru",
                "123456789");

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Nested
    class UpdateTest {

        @Test
        void updateTest_shouldUpdateUserFirstName() throws Exception {
            //given
            UserUpdateDto userUpdateDto = new UserUpdateDto();
            userUpdateDto.setId(5L);
            userUpdateDto.setFirstName("Hello");

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users")
                            .content(mapper.writeValueAsString(userUpdateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(true))
                    .andExpect(status().isOk())
                    .andReturn();

            mockMvc.perform(MockMvcRequestBuilders.get("/users/5"))
                    .andExpect(jsonPath("$.id").value(5))
                    .andExpect(jsonPath("$.firstName").value(userUpdateDto.getFirstName()))
                    .andExpect(status().isOk());

            //then
            Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
        }

        @Test
        void updateTest_shouldUpdateUserFirstNameAndLastName() throws Exception {
            //given
            UserUpdateDto userUpdateDto = new UserUpdateDto();
            userUpdateDto.setId(5L);
            userUpdateDto.setFirstName("Bye");
            userUpdateDto.setLastName("Hello");

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users")
                            .content(mapper.writeValueAsString(userUpdateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(true))
                    .andExpect(status().isOk())
                    .andReturn();

            mockMvc.perform(MockMvcRequestBuilders.get("/users/5"))
                    .andExpect(jsonPath("$.id").value(5))
                    .andExpect(jsonPath("$.firstName").value(userUpdateDto.getFirstName()))
                    .andExpect(jsonPath("$.lastName").value(userUpdateDto.getLastName()))
                    .andExpect(status().isOk());

            //then
            Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
        }

        @Test
        void updateTest_shouldUpdateAllUserFields() throws Exception {
            //given
            UserUpdateDto userUpdateDto = new UserUpdateDto();
            userUpdateDto.setId(5L);
            userUpdateDto.setFirstName("Bye");
            userUpdateDto.setLastName("Hello");
            userUpdateDto.setAge(55);
            userUpdateDto.setEmail("email");
            userUpdateDto.setPassword("password");


            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users")
                            .content(mapper.writeValueAsString(userUpdateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(true))
                    .andExpect(status().isOk())
                    .andReturn();

            mockMvc.perform(MockMvcRequestBuilders.get("/users/5"))
                    .andExpect(jsonPath("$.id").value(5))
                    .andExpect(jsonPath("$.firstName").value(userUpdateDto.getFirstName()))
                    .andExpect(jsonPath("$.lastName").value(userUpdateDto.getLastName()))
                    .andExpect(jsonPath("$.age").value(userUpdateDto.getAge()))
                    .andExpect(jsonPath("$.email").value(userUpdateDto.getEmail()))
                    .andExpect(status().isOk());

            //then
            Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
        }
    }

    @Test
    void deleteTest_shouldDeleteUserAndReturnTrue() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/4"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/4"))
                .andExpect(jsonPath("$.status").value("HTTP status: 400"))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(status().isBadRequest());

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void findUserOrdersTest_shouldReturnListContainingOrderWithId1() throws Exception {
        //given
        Order order = TestUtils.getOrder(1);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/1/orders"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(order.getId()))
                .andExpect(jsonPath("$[0].createDate").isNotEmpty())
                .andExpect(jsonPath("$[0].cost").value(order.getCost()))
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void findUserOrderTest_shouldReturnSingleOrderWithId2OfUserWithId2() throws Exception {
        //given
        Order order = TestUtils.getOrder(2);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/2/orders/2"))
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.createDate").isNotEmpty())
                .andExpect(jsonPath("$.cost").value(order.getCost()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void addOrderToUserTest_shouldAddOrderToUserAndReturnTrue() throws Exception {
        //given
        GiftCertificate certificate = TestUtils.getCertificate(10);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users/3/orders?certificateId=10"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/3/orders/16"))
                .andExpect(jsonPath("$.id").value(16))
                .andExpect(jsonPath("$.createDate").isNotEmpty())
                .andExpect(jsonPath("$.cost").value(certificate.getPrice()))
                .andExpect(jsonPath("$.certificate.id").value(certificate.getId()))
                .andExpect(status().isOk());

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void deleteOrderFromUserTest_shouldDeleteOrderFromUserAndReturnTrue() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/2/orders/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/2/orders/3"))
                .andExpect(jsonPath("$.status").value("HTTP status: 400"))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/3"))
                .andExpect(jsonPath("$.status").value("HTTP status: 400"))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(status().isBadRequest());

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}