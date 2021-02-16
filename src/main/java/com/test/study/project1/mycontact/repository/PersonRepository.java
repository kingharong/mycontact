package com.test.study.project1.mycontact.repository;

import com.test.study.project1.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {
    // JpaRepository 상속받으면 자동으로 레포지토리로 인식
    // @Repository 선언 필요 X
    List<Person> findByName(String name);
    List<Person> findByBlockIsNull();
    List<Person> findByBloodType(String bloodType);
    //List<Person> findByBirthdayBetween(LocalDate start, LocalDate end);
    //JPQL
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday=?1")
    List<Person> findByMonthOfBirthday(int monthOfBirthday); //물음표 뒤 1은 첫 번째 인자를 의미

    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday and " +
            "person.birthday.dayOfBirthday=:dayOfBirthday")
    List<Person> findByMonthAndDayOfBirthDay(@Param("monthOfBirthday") int monthOfBirthday,
                                             @Param("dayOfBirthday") int dayOfBirthday);
   // @Query(value = "select * from person where month_of_birthday= :monthOfBirthday and day_of_birthday=:dayOfBirthday",nativeQuery = true)
}
