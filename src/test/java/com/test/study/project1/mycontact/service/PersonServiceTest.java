package com.test.study.project1.mycontact.service;

import com.test.study.project1.mycontact.domain.Block;
import com.test.study.project1.mycontact.domain.Person;
import com.test.study.project1.mycontact.repository.BlockRepository;
import com.test.study.project1.mycontact.repository.PersonRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Test
    //@Disabled -> 테스트 돌릴 떄 무시
    void getPeopleExcludeBlocks(){
        /*
        givenPeople();
        givenBlocks();

        List<Person> result = personService.getPeopleExcludeBlocks();
        result.forEach(System.out::println);

         */
        List<Person> result = personService.getPeopleExcludeBlocks();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("cathy");
        assertThat(result.get(2).getName()).isEqualTo("cindy");
    }

    @Test
    @Transactional
    void getPeopleByName(){
       // givenPeople();
        List<Person> result = personService.getPeopleByName("cathy");
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAge()).isEqualTo(9);
    }
    /*
    @Test
    void cascadeTest(){
        givenPeople();
        List<Person> result = personRepository.findAll();
        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());   //cascade merge
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

        // cascade remove
//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);

        person.setBlock(null);   //orphanRemoval true로 해야
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println); // 블럭도 같이 사라짐

    }

     */

    @Test
    void getPerson(){
    //    givenPeople();
        Person person = personService.getPerson(4L);
        assertThat(person.getName()).isEqualTo("emma");
    }
    private void givenBlocks() {
        givenBlock("martin");
    }

    private Block givenBlock(String name) {
       return blockRepository.save(Block.builder().name(name).build());
    }



    private void givenPeople() {
        givenPerson("martin",22,"A");
        givenPerson("david", 9,"B");
        givenPerson("dennis", 11, "AB");
        givenBlockPerson("cathy", 12, "O");
    }

    private void givenPerson(String name, int age, String bloodType) {
        personRepository.save(Person.builder().name(name).bloodType(bloodType).build());

    }

    private void givenBlockPerson(String name, int age, String bloodType){
        Person blockPerson = Person.builder()
                .name(name)
                .bloodType(bloodType)
                .block(Block.builder().name(name).build())  //cascade persist
                .build();
        personRepository.save(blockPerson);
    }

}