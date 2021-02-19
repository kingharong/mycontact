package com.test.study.project1.mycontact.service;

import com.test.study.project1.mycontact.controller.dto.PersonDto;
import com.test.study.project1.mycontact.domain.Block;
import com.test.study.project1.mycontact.domain.Person;
import com.test.study.project1.mycontact.domain.dto.Birthday;
import com.test.study.project1.mycontact.repository.BlockRepository;
import com.test.study.project1.mycontact.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
   // private final BlockRepository blockRepository;

    public List<Person> getPeopleExcludeBlocks(){

        return personRepository.findByBlockIsNull();
       // List<Person> people = personRepository.findAll();
     //   List<Block> blocks = blockRepository.findAll();
       // List<String> blockNames = blocks.stream()
         //       .map(Block::getName)
           //     .collect(Collectors.toList());
       // return people.stream().filter(person->person.getBlock()==null)
           //     .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)  //lazyinitializationexception 방지
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).orElseGet(null);
        log.info("person: {}",person);
        return person;
    }

    //@Transactional(readOnly = true)
    public List<Person> getPeopleByName(String name){
        return personRepository.findByName(name);
     //   List<Person> people = personRepository.findAll();
      //  Hibernate.initialize(people.stream().map(Person::getBlock));
     //   return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
    }

    @Transactional
    public void put(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto person) {
        Person persondb = personRepository.findById(id).orElseThrow(()->new RuntimeException("존재하지 않는 사용자"));

        if (!persondb.getName().equals(person.getName())){
            throw new RuntimeException("이름이 다릅니다");
        }
        persondb.setName(person.getName())
                .setAddress(person.getAddress())
                .setAge(person.getAge())
                .setBloodType(person.getBloodType())
                .setHobby(person.getHobby())
                .setJob(person.getJob());
        if (person.getBirthday()!=null){
            persondb.setBirthday(new Birthday(person.getBirthday()));
        }
        personRepository.save(persondb);
    }

    @Transactional
    public void modify(Long id, String name){
        Person person = personRepository.findById(id).orElseThrow(()-> new RuntimeException("아이디가 존재하지 않음"));
        person.setName(name);
        personRepository.save(person);
    }
    @Transactional
    public void delete(Long id) {
        //personRepository.deleteById(id);
        Person person = personRepository.findById(id).orElseThrow(()->new RuntimeException("존재하지 않음"));
        person.setDeleted(true);
        personRepository.save(person);
    }
}
