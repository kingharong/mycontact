package com.test.study.project1.mycontact.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Birthday  {
    private Integer yearOfBirthday;
    private Integer monthOfBirthday;
    private Integer dayOfBirthday;
    //int 는 not null, Integer는 null 허용

    public Birthday(LocalDate birthday){
        this.dayOfBirthday=birthday.getDayOfMonth();
        this.monthOfBirthday=birthday.getMonthValue();
        this.yearOfBirthday=birthday.getYear();
    }


    public static Birthday of(LocalDate birthday){
        return new Birthday(birthday);
    }

}
