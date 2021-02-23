package com.test.study.project1.mycontact.controller;

import com.test.study.project1.mycontact.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HelloWorldControllerTest {
    @Autowired
    private HelloWorldController helloWorldController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;
    @BeforeEach
    void beforeEach(){
        /*
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .setControllerAdvice(globalExceptionHandler)
                .build();

         */
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void helloWorld() {
        System.out.println(helloWorldController.helloWorld()); //sout 치면 자동 완성
        assertThat(helloWorldController.helloWorld()).isEqualTo("hello world");
    }
    @Test
    void helloWorldTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloworld")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello world"));
    }
    @Test
    void helloException() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloexception"))
                .andExpect(status().isInternalServerError());
    }

}