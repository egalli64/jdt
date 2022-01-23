package com.example.jdt.s6;

import java.io.Serial;
import java.util.Set;

public class DogOwner extends Person {
    @Serial
    private static final long serialVersionUID = 4107673229461141934L;
    private Set<Dog> dogs;

    public DogOwner() {
    }

    public DogOwner(String firstName, String lastName, Set<Dog> dogs) {
        super(firstName, lastName);
        this.dogs = dogs;
    }

    public Set<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(Set<Dog> dogs) {
        this.dogs = dogs;
    }

    @Override
    public String toString() {
        return "DogOwner [dogs=" + dogs + ", " + super.toString() + "]";
    }
}
