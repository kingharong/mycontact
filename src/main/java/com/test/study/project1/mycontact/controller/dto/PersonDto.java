package com.test.study.project1.mycontact.controller.dto;

import com.test.study.project1.mycontact.domain.dto.Birthday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Accessors(chain = true)
public class PersonDto {
    @NotBlank(message = "이름은 필수 값입니다")  //NotEmpty는 " "과 같은 공백 문자 검증을 하지 못함
    private String name;
    private String hobby;
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String job;
    private String phoneNumber;

}
