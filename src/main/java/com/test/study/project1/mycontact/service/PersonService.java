package com.test.study.project1.mycontact.service;

import com.test.study.project1.mycontact.domain.Block;
import com.test.study.project1.mycontact.domain.Person;
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
        Person person = personRepository.findById(id).get();
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
}
