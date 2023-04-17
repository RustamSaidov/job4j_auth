package ru.job4j.auth.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePersonService implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public boolean deleteById(int id) {
        return personRepository.deleteById(id);
    }

    @Override
    public boolean update(Person person) {
        return personRepository.save(person).getId() != 0;
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}
