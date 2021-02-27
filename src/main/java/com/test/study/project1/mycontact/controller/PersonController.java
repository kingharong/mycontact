package com.test.study.project1.mycontact.controller;

import com.test.study.project1.mycontact.controller.dto.PersonDto;
import com.test.study.project1.mycontact.domain.Person;

import com.test.study.project1.mycontact.repository.PersonRepository;
import com.test.study.project1.mycontact.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
@Slf4j

public class PersonController {
    private final PersonService personService;
    private final PersonRepository personRepository;

    @GetMapping
    public Page<Person> getAll(@PageableDefault Pageable pageable) {
        return personService.getAll(pageable);
    }


    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody @Valid PersonDto person){
        personService.put(person);
        log.info("person : {}",personRepository.findAll());
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto person){
            personService.modify(id, person);


    }

    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, String name) {
            personService.modify(id, name);

    }
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
        log.info("person : {}",personRepository.findAll());



    }


}
