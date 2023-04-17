package ru.job4j.auth.service;

import ru.job4j.auth.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person save(Person person);

    boolean deleteById(int id);

    boolean update(Person person);

    Optional<Person> findById(int id);

    List<Person> findAll();

    Optional<Person> findByUsername(String username);
}
