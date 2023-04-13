package ru.job4j.url;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserStore users;
    private BCryptPasswordEncoder encoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;

    public UserController(UserStore users, BCryptPasswordEncoder encoder, ObjectMapper objectMapper) {
        this.users = users;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        if (person.getUsername() == null || person.getPassword() == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (person.getPassword().length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        users.save(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return users.findAll();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }

    @GetMapping
    public Person findByUsername(@RequestParam String username) {
        return users.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account is not found. Please, check requisites!!!!!!!!!"
                ));
    }

}