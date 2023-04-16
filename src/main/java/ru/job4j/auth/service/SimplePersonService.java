package ru.job4j.auth.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.HibPersonRepository;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SimplePersonService {

/*
    private final PersonRepository personRepository;

    public SimplePersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
 */


    private final HibPersonRepository personRepository;

    public SimplePersonService(HibPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void save(Person person) {
        personRepository.save(person);
/*
        Optional<Person> result = personRepository.save(person);
        if (result.isEmpty()) {
            throw new NoSuchElementException("Person does not saved");
        }
        return result.get();
 */
    }

/*
    public void update(Person person) {
        boolean result = personRepository.update(person);
        if (!result) {
            throw new NoSuchElementException("Person with this id is not found");
        }
    }

 */

    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public List<Person> findAll() {
        List<Person> persons = (List<Person>) personRepository.findAll();
        return persons;
    }
}

