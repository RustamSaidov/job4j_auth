package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.service.SimplePersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final SimplePersonService persons;
    private BCryptPasswordEncoder encoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());
    private final ObjectMapper objectMapper;

    public PersonController(SimplePersonService persons, BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.persons = persons;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("PERSON FROM sign-up:" + person);
        if (person.getUsername() == null || person.getPassword() == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (person.getPassword().length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        persons.save(person);
    }

    /*
    @GetMapping("/all")
    public List<Person> findAll() {
        return users.findAll();
    }

     */

    /*GET с использованием ResponseEntity:*/
    @GetMapping("/all")
    public ResponseEntity<List<Person>> example2() {
        return ResponseEntity.of(Optional.of(persons.findAll()));
    }


    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }

/*
    @GetMapping
    public Person findByUsername(@RequestParam String username) {
        return users.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account is not found. Please, check requisites!!!!!!!!!"
                ));
    }
 */

    /*GET с использованием ResponseEntity:*/
    @GetMapping
    public ResponseEntity<Person> findByUsername(@RequestParam String username) {
        var user = persons.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account is not found. Please, check requisites!!!!!!!!!"
                ));
        return ResponseEntity.of(Optional.of(user));

    }

    /*Пример использования PATCH метода для частичного обновления данных:*/
    @PatchMapping("/change-person")
    public Person changePersonUsingPatchMethod(@RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        var current = persons.findByUsername(person.getUsername());
        if (current.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        persons.save(person);
        return persons.findByUsername(person.getUsername()).get();
    }
}