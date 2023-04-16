package ru.job4j.auth.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.auth.model.Person;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class HibPersonRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param person персона.
     * @return Optional of person.
     */
    public Optional<Person> save(Person person) {
        System.out.println("PERSON FROM REPO:" + person);
        try {
            crudRepository.run(session -> session.persist(person));
        } catch (Exception exception) {
            return Optional.empty();
        }
        return Optional.ofNullable(person);
    }

    /**
     * Удалить персону по id.
     *
     * @return boolean.
     */
    /*
    public boolean deleteById(int personId) {
        try {
            crudRepository.run(
                    "delete from Person where id = :fId",
                    Map.of("fId", personId));
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
     */

    /**
     * Обновить в базе персону.
     *
     * @param person персона.
     * @return boolean.
     */
    public boolean update(Person person) {
        try {
            crudRepository.run(session -> session.merge(person));
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Найти персону по id.
     *
     * @param id ID.
     * @return Optional of person.
     */
    /*
    @Override
    public Optional<Person> findById(int id) {
        Optional<Person> result = Optional.empty();
        try {
            result = crudRepository.optional("""
                             from Person as t WHERE t.id = :fId
                            """, Person.class,
                    Map.of("fId", id));
        } catch (Exception exception) {
            return result;
        }
        return result;
    }
     */

    /**
     * Найти персону по username.
     *
     * @param username name of Person.
     * @return Optional of person.
     */
    public Optional<Person> findByUsername(String username) {
        Optional<Person> result = Optional.empty();
        try {
            result = crudRepository.optional("""
                             from Person as p WHERE p.username = :fUsername
                            """, Person.class,
                    Map.of("fUsername", username));
        } catch (Exception exception) {
            return result;
        }
        return result;
    }

    /**
     * Список персон отсортированных по id.
     *
     * @return список персон.
     */
    public Collection<Person> findAll() {
        var list = crudRepository.query("FROM Person p ORDER BY p.id", Person.class);
        return list.stream().distinct().collect(Collectors.toList());
    }
}
