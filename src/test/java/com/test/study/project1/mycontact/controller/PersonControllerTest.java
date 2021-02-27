package com.test.study.project1.mycontact.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.study.project1.mycontact.controller.dto.PersonDto;
import com.test.study.project1.mycontact.domain.Person;
import com.test.study.project1.mycontact.domain.dto.Birthday;
import com.test.study.project1.mycontact.exception.handler.GlobalExceptionHandler;
import com.test.study.project1.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        /*
        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setMessageConverters(messageConverter)
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .setControllerAdvice(globalExceptionHandler)
                .alwaysDo(print())
                .build();

         */
        mockMvc = MockMvcBuilders.
                webAppContextSetup(wac)
             .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void getPerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"));
    }


    @Test
    void postPerson() throws Exception {
        PersonDto dto = PersonDto.of("martin","게임","A","d",LocalDate.now(),"프로그래머","00011112222");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJsonString(dto)))
                .andExpect(status().isCreated());
        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);
        assertAll(
                ()-> assertThat(result.getName()).isEqualTo("martin"),
                ()-> assertThat(result.getAddress()).isEqualTo("d"),
                ()-> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now()))
        );
    }
    @Test
    void postPersonIfNameIsNull() throws Exception {
        PersonDto personDto = new PersonDto();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJsonString(personDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void postPersonIfNameIsEmpty() throws Exception {
        PersonDto personDto = PersonDto.builder()
                .name("").build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJsonString(personDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름은 필수 값입니다"));
    }

    @Test
    void modifyPerson() throws Exception {
        PersonDto dto = PersonDto.of("martin", "게임","A","서울",
                LocalDate.now(),"프로게이머","01033232323" );
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJsonString(dto)))
                .andExpect(status().isOk());
        Person result = personRepository.findById(1L).get();
        assertAll(
                ()-> assertThat(result.getName()).isEqualTo("martin"),
                ()-> assertThat(result.getJob()).isEqualTo("프로게이머"));
        //assertThrows

    }
    @Test
    void modifyName() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .param("name", "martin22"))
                .andExpect(status().isOk());
        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martin22");

    }
    @Test
    void modifyPersonIfNameIsDifferent() throws Exception {
        PersonDto personDto = PersonDto.builder().name("james").hobby("game").build();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJsonString(personDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름을 변경 허용하지 않습니다"));
    }
    @Test
    void modifyPersonIfPersonNotFound() throws Exception {
        PersonDto personDto = PersonDto.builder().name("cathy").hobby("singing").build();
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJsonString(personDto)))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Person Entity가 존재하지 않습니다"));
    }
    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andExpect(status().isOk());
        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
        log.info("people deleted: {}",personRepository.findPeopleDeleted());
    }

    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
    @Test
    void checkJson() throws JsonProcessingException {
        PersonDto dto = new PersonDto();
        dto.setName("ff");
        dto.setBirthday(LocalDate.now());
        System.out.println(">>>"+toJsonString(dto));
    }
    @Test
    void getAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person")
                .param("page","1")
                .param("size","2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.totalElements").value(6))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.content.[0].name").value("cindy"));
    }
}