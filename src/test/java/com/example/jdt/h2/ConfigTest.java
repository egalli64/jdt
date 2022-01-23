package com.example.jdt.h2;

import static com.example.jdt.h2.Config.PASSWORD;
import static com.example.jdt.h2.Config.URL;
import static com.example.jdt.h2.Config.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ConfigTest {
    private static Connection connection;

    @BeforeAll
    static void init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            fail("Can't get connection", e);
        }
    }

    @AfterAll
    static void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            fail("Can't close connection", e);
        }
    }

    @Test
    void product() {
        try {
            DatabaseMetaData dmd = connection.getMetaData();

            String db = dmd.getDatabaseProductName();
            assertThat(db).isEqualTo("H2");

            String version = dmd.getDatabaseProductVersion();
            assertThat(version).startsWith("2.1");
        } catch (SQLException e) {
            fail("Failure on metadata", e);
        }
    }

    @Test
    void catalog() {
        try {
            String catalog = connection.getCatalog();
            assertThat(catalog).isEqualTo("JDT");
        } catch (SQLException e) {
            fail("Failure on metadata", e);
        }
    }
}
