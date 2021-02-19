package com.test.study.project1.mycontact.controller.dto;

import com.test.study.project1.mycontact.domain.dto.Birthday;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
@Data
public class PersonDto {
    private String name;
    private int age;
    private String hobby;
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;

}
