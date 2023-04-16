package ru.job4j.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {
    private PersonRepository persons;

    public PersonDetailsServiceImpl(PersonRepository persons) {
        this.persons = persons;
    }

//    private final HibPersonRepository persons;
//
//    public PersonDetailsServiceImpl(HibPersonRepository persons) {
//        this.persons = persons;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = persons.findByUsername(username);
        System.out.println("PERSON FROM PersonDetailsServiceImpl:" + person);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(person.get().getUsername(), person.get().getPassword(), emptyList());
    }
}