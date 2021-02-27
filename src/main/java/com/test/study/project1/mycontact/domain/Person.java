package com.test.study.project1.mycontact.domain;

import com.test.study.project1.mycontact.domain.dto.Birthday;
import lombok.*;
import lombok.experimental.Accessors;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Where(clause = "deleted = false")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String hobby;

    private String bloodType;

    private String address;

    @ColumnDefault("0")
    private boolean deleted;

    @Embedded
    @Valid
    private Birthday birthday;

    private String job;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Block block;
    //@ToString.Exclude 쓰면 블럭에 대한 쿼리문이 나오지 않음
    //optional false를 하면 반드시 person을 만들 때 block이 있어야 함
    //그리고 fetchtype eager로 하면 inner join 일어남



    public Integer getAge(){
        if (this.birthday!=null) {
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        }
        return null;
    }
    public boolean isBirthDayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(),
                this.birthday.getMonthOfBirthday(), this.birthday.getDayOfBirthday()));
    }

}
