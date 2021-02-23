package com.test.study.project1.mycontact.controller;


import com.test.study.project1.mycontact.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Controller + ResponseBody
public class HelloWorldController {
    @GetMapping ("/api/helloworld")
    public String helloWorld(){
        return "hello world";  //Ctrl+Shift+T 누르면 테스트 생성
    }

    @GetMapping("/api/helloexception")
    public String helloException(){
        throw new RuntimeException("Hello RuntimeException");
    }


}


