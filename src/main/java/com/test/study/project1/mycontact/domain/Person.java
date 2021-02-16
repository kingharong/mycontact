package com.test.study.project1.mycontact.domain;

import com.test.study.project1.mycontact.domain.dto.Birthday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int age;

    private String hobby;

    private String bloodType;

    private String address;

    @Embedded
    @Valid
    private Birthday birthday;

    private String job;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Block block;
    //@ToString.Exclude 쓰면 블럭에 대한 쿼리문이 나오지 않음
    //optional false를 하면 반드시 person을 만들 때 block이 있어야 함
    //그리고 fetchtype eager로 하면 inner join 일어남





}
