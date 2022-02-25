package com.example.jdt.s7;

import java.time.LocalDate;
import java.util.Set;

public class MainDb {
    public static void main(String[] args) {
        int pid = create();
        System.out.println("Person has id " + pid);

        DogOwner tom = read(pid);
        System.out.println("Tom: " + tom);
    }

    private static DogOwner read(int pid) {
        return DogAssemblerDb.read(pid);
    }

    private static int create() {
        Set<Dog> dogs = Set.of(new Dog("Bob", LocalDate.now()), new Dog("Bix", LocalDate.of(2020, 10, 20)));
        Person tom = new Person("Tom", "Smith");
        System.out.println("Saving " + tom);

        return DogAssemblerDb.save(tom, dogs);
    }
}
