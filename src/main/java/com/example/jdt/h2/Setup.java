package com.example.jdt.h2;

import static com.example.jdt.h2.Config.PASSWORD;
import static com.example.jdt.h2.Config.URL;
import static com.example.jdt.h2.Config.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Setup {
    static final String DROP_DOG_TABLE = "drop table if exists dog";
    static final String DROP_OWNER_TABLE = "drop table if exists owner";
    static final String CREATE_OWNER_TABLE = """
            create table owner (
            owner_id identity primary key,
            first_name varchar(20),
            last_name varchar(20))""";
    static final String CREATE_DOG_TABLE = """
            create table dog (
            dog_id identity primary key,
            name varchar(20),
            owner_id integer,
            constraint dog_owner_fk foreign key (owner_id) references owner (owner_id))""";

    public static void main(String[] args) {
        dropTables();
        createTables();
    }

    private static void createTables() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_OWNER_TABLE);
            stmt.execute(CREATE_DOG_TABLE);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static void dropTables() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
            stmt.execute(DROP_DOG_TABLE);
            stmt.execute(DROP_OWNER_TABLE);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
