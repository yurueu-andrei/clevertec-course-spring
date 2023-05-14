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
import ru.clevertec.ecl.dto.CertificateSaveDto;
import ru.clevertec.ecl.dto.CertificateUpdateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.utils.TestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CertificateControllerTest extends BaseControllerTest {

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
    void findByIdTest_shouldReturnCertificateWithId5() throws Exception {
        //given
        GiftCertificate certificate = TestUtils.getCertificate(5);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/certificates/5"))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.name").value(certificate.getName()))
                .andExpect(jsonPath("$.description").value(certificate.getDescription()))
                .andExpect(jsonPath("$.price").value(certificate.getPrice()))
                .andExpect(jsonPath("$.duration").value(certificate.getDuration()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void findAllTest_shouldReturnCertificatesWithIdFrom3To4() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/certificates?page=1&size=2"))
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
    void addTest_shouldAddCertificate() throws Exception {
        //given
        List<TagDto> tagDtos = List.of(
                new TagDto("health"),
                new TagDto("rest"));

        CertificateSaveDto certificate = new CertificateSaveDto(
                "name",
                "desc",
                BigDecimal.valueOf(1.11),
                111,
                tagDtos);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/certificates")
                        .content(mapper.writeValueAsString(certificate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.name").value(certificate.getName()))
                .andExpect(jsonPath("$.description").value(certificate.getDescription()))
                .andExpect(jsonPath("$.price").value(certificate.getPrice()))
                .andExpect(jsonPath("$.duration").value(certificate.getDuration()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Nested
    class UpdateTest {

        @Test
        void updateTest_shouldUpdateCertificateName() throws Exception {
            //given
            CertificateUpdateDto certificateUpdateDto = new CertificateUpdateDto();
            certificateUpdateDto.setId(5L);
            certificateUpdateDto.setName("Hello");

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/certificates")
                            .content(mapper.writeValueAsString(certificateUpdateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(true))
                    .andExpect(status().isOk())
                    .andReturn();

            mockMvc.perform(MockMvcRequestBuilders.get("/certificates/5"))
                    .andExpect(jsonPath("$.id").value(5))
                    .andExpect(jsonPath("$.name").value(certificateUpdateDto.getName()))
                    .andExpect(status().isOk());

            //then
            Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
        }

        @Test
        void updateTest_shouldUpdateCertificateNameAndDescription() throws Exception {
            //given
            CertificateUpdateDto certificateUpdateDto = new CertificateUpdateDto();
            certificateUpdateDto.setId(5L);
            certificateUpdateDto.setName("Bye");
            certificateUpdateDto.setDescription("Hello");

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/certificates")
                            .content(mapper.writeValueAsString(certificateUpdateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(true))
                    .andExpect(status().isOk())
                    .andReturn();

            mockMvc.perform(MockMvcRequestBuilders.get("/certificates/5"))
                    .andExpect(jsonPath("$.id").value(5))
                    .andExpect(jsonPath("$.name").value(certificateUpdateDto.getName()))
                    .andExpect(jsonPath("$.description").value(certificateUpdateDto.getDescription()))
                    .andExpect(status().isOk());

            //then
            Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
        }

        @Test
        void updateTest_shouldUpdateAllCertificateFields() throws Exception {
            //given
            CertificateUpdateDto certificateUpdateDto = new CertificateUpdateDto();
            certificateUpdateDto.setId(5L);
            certificateUpdateDto.setName("Bye");
            certificateUpdateDto.setDescription("Hello");
            certificateUpdateDto.setPrice(BigDecimal.valueOf(5.55));
            certificateUpdateDto.setDuration(222);
            certificateUpdateDto.setTags(List.of(new TagDto("hello"), new TagDto("health")));


            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/certificates")
                            .content(mapper.writeValueAsString(certificateUpdateDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").value(true))
                    .andExpect(status().isOk())
                    .andReturn();

            mockMvc.perform(MockMvcRequestBuilders.get("/certificates/5"))
                    .andExpect(jsonPath("$.id").value(5))
                    .andExpect(jsonPath("$.name").value(certificateUpdateDto.getName()))
                    .andExpect(jsonPath("$.description").value(certificateUpdateDto.getDescription()))
                    .andExpect(jsonPath("$.price").value(certificateUpdateDto.getPrice()))
                    .andExpect(jsonPath("$.duration").value(certificateUpdateDto.getDuration()))
                    .andExpect(jsonPath("$.tags").isArray())
                    .andExpect(status().isOk());

            //then
            Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
        }
    }

    @Test
    void deleteTest_shouldDeleteCertificateWithId4() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/certificates/7"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/certificates/7"))
                .andExpect(jsonPath("$.status").value("HTTP status: 400"))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(status().isBadRequest());

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}