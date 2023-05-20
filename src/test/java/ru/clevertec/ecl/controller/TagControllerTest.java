package ru.clevertec.ecl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.TagUpdateDto;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.utils.TestUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TagControllerTest extends BaseControllerTest {

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
    void findByIdTest_shouldReturnTagWithId12() throws Exception {
        //given
        Tag tag = TestUtils.getTag(12);
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tags/12"))
                .andExpect(jsonPath("$.name").value(tag.getName()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void findAllTest_shouldReturnAllTags() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tags?page=1&size=7"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(TestUtils.getTag(8).getName()))
                .andExpect(jsonPath("$[1].name").value(TestUtils.getTag(9).getName()))
                .andExpect(jsonPath("$[2].name").value(TestUtils.getTag(10).getName()))
                .andExpect(jsonPath("$[3].name").value(TestUtils.getTag(11).getName()))
                .andExpect(jsonPath("$[4].name").value(TestUtils.getTag(12).getName()))
                .andExpect(jsonPath("$[5].name").value(TestUtils.getTag(13).getName()))
                .andExpect(jsonPath("$[6].name").value(TestUtils.getTag(14).getName()))
                .andExpect(jsonPath("$[7]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void addTest_shouldAddTag() throws Exception {
        //given
        TagDto tag = new TagDto("new tag");

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .content(mapper.writeValueAsString(tag))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(tag.getName()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void updateTest_shouldUpdateTagName() throws Exception {
        //given
        TagUpdateDto tagUpdateDto = new TagUpdateDto(17L, "2022");

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/tags")
                        .content(mapper.writeValueAsString(tagUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/tags/17"))
                .andExpect(jsonPath("$.name").value(tagUpdateDto.getName()))
                .andExpect(status().isOk());

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }


    @Test
    void deleteTest_shouldDeleteTagAndReturnTrue() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/tags/19"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/tags/19"))
                .andExpect(jsonPath("$.status").value("HTTP status: 400"))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(status().isBadRequest());

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void findTheMostUsedTagTest_shouldReturnTheMostUsedTagOfTheUserWithTheHighestOrderCostsSum() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/tags/mostUsed"))
                .andExpect(jsonPath("$.name").value("rest"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}