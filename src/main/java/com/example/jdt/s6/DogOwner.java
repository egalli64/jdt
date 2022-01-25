package com.example.jdt.s6;

import java.io.Serial;
import java.util.Set;

public class DogOwner extends Person {
    @Serial
    private static final long serialVersionUID = 4107673229461141934L;
    private int key;
    private Set<Dog> dogs;

    public DogOwner() {
        this(0);
    }

    public DogOwner(int key) {
        this.key = key;
    }

    public DogOwner(int key, String firstName, String lastName, Set<Dog> dogs) {
        super(firstName, lastName);
        this.key = key;
        this.dogs = dogs;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Set<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(Set<Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public String toString() {
        return "DogOwner [key=" + key + ", " + super.toString() + ", dogs=" + dogs + "]";
    }
}
