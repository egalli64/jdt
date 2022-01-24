package com.example.jdt.s6;

import java.time.LocalDate;
import java.util.Set;

public class MainFile {
    public static void main(String[] args) {
        serializeDogs();

        DogOwner tom = DogAssemblerFile.read("tom");
        System.out.println("Deserialized tom: " + tom);
    }

    private static void serializeDogs() {
        Set<Dog> dogs = Set.of(new Dog("Bob", LocalDate.now()), new Dog("Bix", LocalDate.of(2020, 10, 20)));
        Person tom = new Person("Tom", "Smith");

        DogAssemblerFile.save("tom", tom, dogs);
        System.out.println("Serializing done");
    }
}
