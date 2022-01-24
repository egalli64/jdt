package com.example.jdt.s6;

import static com.example.jdt.h2.Config.PASSWORD;
import static com.example.jdt.h2.Config.URL;
import static com.example.jdt.h2.Config.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

public class DogAssemblerDb {
    private static final String INSERT_OWNER = "insert into owner (first_name, last_name) values (?, ?)";
    private static final String INSERT_DOG = "insert into dog (name, birth, owner_id) values (?, ?, ?)";
    private static final String SELECT_OWNER = """
            select owner_id, first_name, last_name
            from owner
            where first_name = ? and last_name = ?""";

    public static DogOwner read(int key) {
        return null;
    }

    public static int save(Person person, Set<Dog> dogs) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement psSel = conn.prepareStatement(SELECT_OWNER)) {

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
