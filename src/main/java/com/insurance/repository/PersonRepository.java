package com.insurance.repository;


import org.springframework.data.repository.CrudRepository;

import com.insurance.dto.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByName(String name);
}