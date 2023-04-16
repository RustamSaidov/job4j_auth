package ru.job4j.auth.repository;

import org.springframework.stereotype.Component;
import ru.job4j.auth.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PersonRepository {
    private final ConcurrentHashMap<String, Person> persons;

    public PersonRepository() {
        Person person1 = new Person(1, "first", "first", "111111");
        Person person2 = new Person(2, "second", "second", "222222");
        Person person3 = new Person(3, "third", "third", "333333");
        persons = new ConcurrentHashMap<>(Map.ofEntries(
                Map.entry(person1.getUsername(), person1),
                Map.entry(person2.getUsername(), person2),
                Map.entry(person3.getUsername(), person3))
        );
    }

    public void save(Person person) {
        persons.put(person.getUsername(), person);
    }

    public Optional<Person> findByUsername(String username) {
        return Optional.ofNullable(persons.get(username));
    }

    public List<Person> findAll() {
        return new ArrayList<>(persons.values());
    }
}