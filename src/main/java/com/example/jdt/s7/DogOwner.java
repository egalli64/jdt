package com.example.jdt.s7;

import java.io.Serializable;
import java.util.Set;

public record DogOwner(int key, Person person, Set<Dog> dogs) implements Serializable {
}
