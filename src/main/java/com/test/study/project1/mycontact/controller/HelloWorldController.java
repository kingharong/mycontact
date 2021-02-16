package com.test.study.project1.mycontact.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Controller + ResponseBody
public class HelloWorldController {
    @GetMapping ("/api/helloworld")
    public String helloWorld(){
        return "hello world";  //Ctrl+Shift+T 누르면 테스트 생성
    }
}
