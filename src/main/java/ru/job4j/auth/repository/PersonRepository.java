package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.job4j.auth.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAll();

    default Optional<Person> findByUsername(String username) {
        var persons = findAll();
        return  persons.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst();
    }

    boolean deleteById(int id);
}