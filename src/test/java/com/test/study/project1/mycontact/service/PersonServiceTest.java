package com.test.study.project1.mycontact.service;

import com.test.study.project1.mycontact.controller.dto.PersonDto;
import com.test.study.project1.mycontact.domain.Block;
import com.test.study.project1.mycontact.domain.Person;
import com.test.study.project1.mycontact.exception.PersonNotFoundException;
import com.test.study.project1.mycontact.repository.BlockRepository;
import com.test.study.project1.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository; //data.sql 적용 X

    @Mock
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
       when(personRepository.findByName("martin"))
               .thenReturn(Lists.newArrayList(Person.builder().name("martin").build()));
       List<Person> result = personService.getPeopleByName("martin");
       assertThat(result.size()).isEqualTo(1);
       assertThat(result.get(0).getName()).isEqualTo("martin");
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
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(Person.builder().name("martin").build()));
        Person person = personService.getPerson(1L);
        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        Person person = personService.getPerson(1L);
        assertThat(person).isNull();
    }

    @Test
    void put(){
        PersonDto dto = PersonDto.of("martin","programming","A","판교",LocalDate.now(),"프로그래머","01011112222");
        personService.put(dto); //mock 테스트이므로 실제 repo에 저장 X
        verify(personRepository, times(1)).save(any(Person.class)); //times 대신 never()도 있음
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
      assertThrows(PersonNotFoundException.class,()->personService.modify(1L,mockPersonDto()));

    }
    @Test
    void modifyIfNameIsDifferent(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(Person.builder().name("tony").build()));
        assertThrows(RuntimeException.class,()->personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(Person.builder().name("martin").build()));
        personService.modify(1L,mockPersonDto());
        //verify(personRepository,times(1)).save(any(Person.class));
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeUpdated()));

    }

    @Test
    void modifyByNameIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(PersonNotFoundException.class, ()->personService.modify(1L,"deny"));
    }
    @Test
    void modifyByName(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(Person.builder().name("martin").build()));
        personService.modify(1L,"deny");
        verify(personRepository,times(1)).save(argThat(new IsNameWillBeUpdated()));

    }
    @Test
    void deleteIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(PersonNotFoundException.class,()->personService.delete(1L));
    }
    @Test
    void deletePerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(Person.builder().name("martin").build()));
        personService.delete(1L);
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin","programming","A","판교",LocalDate.now(),"프로그래머","01011112222");
    }
    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person>{
        @Override
        public boolean matches(Person person) {
            return equals(person.getName(),"martin")
                    && equals(person.getAddress(),"판교");
        }
        private boolean equals(String actual, String expected){
            return expected.equals(actual);
        }
    }
    private static class IsNameWillBeUpdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("deny");
        }
    }
    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
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