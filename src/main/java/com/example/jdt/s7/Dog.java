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

    public static void main(String[] args) {
        Dog dog = new Dog();

        System.out.println("Dog name: " + dog.name());
        System.out.println("Dog birth: " + dog.birth());
        System.out.println("Dog: " + dog);

        System.out.println("Null data members disallowed for Dog");
//        Dog x = new Dog(null, null);
//        System.out.println("Dog name: " + x.name());
//        System.out.println("Dog birth: " + x.birth());
//        System.out.println("Dog: " + dog);
    }
}
