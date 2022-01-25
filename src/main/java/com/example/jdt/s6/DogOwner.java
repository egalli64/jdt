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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((dogs == null) ? 0 : dogs.hashCode());
        result = prime * result + key;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DogOwner other = (DogOwner) obj;
        if (dogs == null) {
            if (other.dogs != null)
                return false;
        } else if (!dogs.equals(other.dogs))
            return false;
        if (key != other.key)
            return false;
        return true;
    }
}
