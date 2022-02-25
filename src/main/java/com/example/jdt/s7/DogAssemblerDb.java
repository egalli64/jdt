package com.example.jdt.s7;

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
            psOwn.setInt(1, key);
            Person person = null;
            try (ResultSet rs = psOwn.executeQuery()) {
                if (rs.first()) {
                    person = new Person(rs.getString(1), rs.getString(2));
                } else {
                    throw new IllegalStateException("Can't get person for key " + key);
                }
            }

            psDog.setInt(1, key);
            HashSet<Dog> dogs = new HashSet<>();
            try (ResultSet rs = psDog.executeQuery()) {
                while (rs.next()) {
                    Object obj = rs.getObject(2);
                    if (!(obj instanceof Date)) {
                        System.out.println("Bad dog birth detected: " + obj);
                    } else {
                        dogs.add(new Dog(rs.getString(1), ((Date) obj).toLocalDate()));
                    }
                }
            }

            return new DogOwner(key, person, dogs);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static int save(Person person, Set<Dog> dogs) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement psSel = conn.prepareStatement(SELECT_OWNER_BY_NAME)) {

            try (PreparedStatement psOwner = conn.prepareStatement(INSERT_OWNER)) {
                psOwner.setString(1, person.firstName());
                psOwner.setString(2, person.lastName());
                psOwner.executeUpdate();
            } catch (JdbcSQLIntegrityConstraintViolationException e) {
                System.out.println("Not inserted: " + e.getMessage());
            }

            int personId;
            psSel.setString(1, person.firstName());
            psSel.setString(2, person.lastName());
            try (ResultSet rs = psSel.executeQuery()) {
                if (rs.first()) {
                    personId = rs.getInt(1);
                } else {
                    throw new IllegalStateException("Can't get id for " + person.firstName());
                }
            }

            try (PreparedStatement psDog = conn.prepareStatement(INSERT_DOG)) {
                dogs.forEach(dog -> {
                    try {
                        psDog.setString(1, dog.name());
                        psDog.setObject(2, dog.birth());
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
