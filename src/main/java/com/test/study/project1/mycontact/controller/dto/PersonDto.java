package com.test.study.project1.mycontact.controller.dto;

import com.test.study.project1.mycontact.domain.dto.Birthday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Accessors(chain = true)
public class PersonDto {
    private String name;
    private String hobby;
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;

}
