package com.example.jdt.s6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        serializeDogs();

        DogOwner tom = deserializeDogs();
        System.out.println("Deserialized tom: " + tom);
    }

    private static DogOwner deserializeDogs() {
        File dump = new File("tom.dogs");
        try (FileInputStream fis = new FileInputStream(dump); //
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof DogOwner) {
                return (DogOwner) obj;
            } else {
                throw new IllegalStateException("Bad object type, " + obj.getClass().getCanonicalName());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Can't deserialize tom", e);
        }

    }

    private static void serializeDogs() {
        Set<Dog> dogs = Set.of(new Dog("Bob", LocalDate.now()), new Dog("Bix", LocalDate.of(2020, 10, 20)));
        DogOwner tom = new DogOwner("Tom", "Smith", dogs);

        File dump = new File("tom.dogs");
        try (FileOutputStream fos = new FileOutputStream(dump); //
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(tom);
        } catch (IOException e) {
            System.err.println("Can't serialize tom: " + e.getMessage());
        }
    }
}
