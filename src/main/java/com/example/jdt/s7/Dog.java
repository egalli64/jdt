package com.example.jdt.s7;

import java.time.LocalDate;

public record Dog(String name, LocalDate birth) {

    public Dog() {
        this("Unknown", LocalDate.now());
    }

    public Dog {
        if (name == null || birth == null) {
            throw new IllegalArgumentException("No null, please!");
        }
    }
}
