package com.test.study.project1.mycontact.repository;

import com.test.study.project1.mycontact.domain.Person;
import com.test.study.project1.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        /*

        Person person = Person.builder()
                .name("john")
                .age(23)
                .bloodType("A")
                .build();
        personRepository.save(person);
        List<Person> people = personRepository.findAll();
        assertThat(people.size()).isEqualTo(6);
        assertThat(people.get(1).getName()).isEqualTo("cathy");
        assertThat(people.get(1).getAge()).isEqualTo(9);
        assertThat(people.get(1).getBloodType()).isEqualTo("O");
*/

    }
    @Test
    void hashCodeAndEquals(){
        Person person1 = Person.builder()
                .build();
        Person person2 = Person.builder()
                .name("martin")
                .build();
        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());
        System.out.println(map);
        System.out.println(map.get(person2));
    }

    @Test
    void findByBloodType(){
        /*
        givenPerson("martin", 10,"A",LocalDate.of(2000,11,11));
        givenPerson("david", 11, "B",LocalDate.of(1991, 8,3));
        givenPerson("dennis", 12, "A",LocalDate.of(1992,8,31));
        givenPerson("emma", 13, "AB",LocalDate.of(1991,8,31));
        List<Person> result = personRepository.findByBloodType("A");
        result.forEach(System.out::println);

         */
        List<Person> result = personRepository.findByBloodType("A");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("jacob");

    }
    @Test
    void findByBirthdayBetween(){
        /*
        givenPerson("martin", 10,"A",LocalDate.of(2000,2,28));
        givenPerson("david", 11, "B",LocalDate.of(1991, 8,3));
        givenPerson("dennis", 12, "A",LocalDate.of(1992,8,31));
        givenPerson("emma", 13, "AB",LocalDate.of(1991,8,31));
        List<Person> result = personRepository.findByMonthOfBirthday(8);
        List<Person> result2 = personRepository.findByMonthAndDayOfBirthDay(8,31);
        result.forEach(System.out::println);
        result2.forEach(System.out::println);

         */
        List<Person> result = personRepository.findByMonthOfBirthday(8);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("cathy");

    }
    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = Person.builder()
                .name(name)
                .bloodType(bloodType)
                .build();
        person.setBirthday(new Birthday(birthday));

        personRepository.save(person);
    }

}