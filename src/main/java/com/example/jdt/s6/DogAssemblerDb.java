package com.example.jdt.s6;

import static com.example.jdt.h2.Config.PASSWORD;
import static com.example.jdt.h2.Config.URL;
import static com.example.jdt.h2.Config.USER;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

public class DogAssemblerDb {
    private static final String INSERT_OWNER = "insert into owner (first_name, last_name) values (?, ?)";
    private static final String INSERT_DOG = "insert into dog (name, birth, owner_id) values (?, ?, ?)";
    private static final String SELECT_OWNER_BY_NAME = """
            select owner_id, first_name, last_name
            from owner
            where first_name = ? and last_name = ?""";
    private static final String SELECT_OWNER_BY_ID = """
            select first_name, last_name
            from owner
            where owner_id = ?""";
    private static final String SELECT_OWNER_DOGS = """
            select name, birth
            from dog
            where owner_id = ?""";

    public static DogOwner read(int key) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement psOwn = conn.prepareStatement(SELECT_OWNER_BY_ID);
                PreparedStatement psDog = conn.prepareStatement(SELECT_OWNER_DOGS)) {

            DogOwner result = new DogOwner(key);
            psOwn.setInt(1, key);
            try (ResultSet rs = psOwn.executeQuery()) {
                if (rs.first()) {
                    result.setFirstName(rs.getString(1));
                    result.setLastName(rs.getString(2));
                } else {
                    throw new IllegalStateException("Can't get person for key " + key);
                }
            }

            psDog.setInt(1, key);
            HashSet<Dog> dogs = new HashSet<>();
            try (ResultSet rs = psDog.executeQuery()) {
                while (rs.next()) {
                    Dog dog = new Dog();
                    dog.setName(rs.getString(1));
                    Object obj = rs.getObject(2);
                    if (obj instanceof Date) {
                        dog.setBirth(((Date) obj).toLocalDate());
                    } else {
                        System.out.println("Bad dog birth detected: " + obj);
                    }
                    dogs.add(dog);
                }
                result.setDogs(dogs);
            }

            return result;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static int save(Person person, Set<Dog> dogs) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement psSel = conn.prepareStatement(SELECT_OWNER_BY_NAME)) {

            try (PreparedStatement psOwner = conn.prepareStatement(INSERT_OWNER)) {
                psOwner.setString(1, person.getFirstName());
                psOwner.setString(2, person.getLastName());
                psOwner.executeUpdate();
            } catch (JdbcSQLIntegrityConstraintViolationException e) {
                System.out.println("Not inserted: " + e.getMessage());
            }

            int personId;
            psSel.setString(1, person.getFirstName());
            psSel.setString(2, person.getLastName());
            try (ResultSet rs = psSel.executeQuery()) {
                if (rs.first()) {
                    personId = rs.getInt(1);
                } else {
                    throw new IllegalStateException("Can't get id for " + person.getFirstName());
                }
            }

            try (PreparedStatement psDog = conn.prepareStatement(INSERT_DOG)) {
                dogs.forEach(dog -> {
                    try {
                        psDog.setString(1, dog.getName());
                        psDog.setObject(2, dog.getBirth());
                        psDog.setInt(3, personId);
                        psDog.executeUpdate();
                    } catch (Exception e) {
                        System.out.println("Dog not inserted: " + e.getMessage());
                    }
                });
            }

            return personId;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
