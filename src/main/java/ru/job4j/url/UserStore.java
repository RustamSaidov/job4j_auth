package ru.job4j.url;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStore {
    private final ConcurrentHashMap<String, Person> users;

    public UserStore() {
        Person person1 = new Person("first", "first", "111111");
        Person person2 = new Person("second", "second", "222222");
        Person person3 = new Person("third", "third", "333333");
        users = new ConcurrentHashMap<>(Map.ofEntries(
                Map.entry(person1.getUsername(), person1),
                Map.entry(person2.getUsername(), person2),
                Map.entry(person3.getUsername(), person3))
        );
    }

    public void save(Person person) {
        users.put(person.getUsername(), person);
    }


    public Optional<Person> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public List<Person> findAll() {
        return new ArrayList<>(users.values());
    }


}