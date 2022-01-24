package com.example.jdt.s6;

import java.time.LocalDate;
import java.util.Set;

public class MainDb {
    public static void main(String[] args) {
        create();

        DogOwner tom = read();
        System.out.println("Tom: " + tom);
    }

    private static DogOwner read() {
        return null;
    }

    private static void create() {
        Set<Dog> dogs = Set.of(new Dog("Bob", LocalDate.now()), new Dog("Bix", LocalDate.of(2020, 10, 20)));
        DogOwner tom = new DogOwner("Tom", "Smith", dogs);

        System.out.println(tom);
    }
}
