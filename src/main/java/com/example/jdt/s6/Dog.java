package com.example.jdt.s6;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Dog implements Serializable {
    @Serial
    private static final long serialVersionUID = 2382613782351318972L;
    private String name;
    private LocalDate birth;

    public Dog(String name, LocalDate birth) {
        this.name = name;
        this.birth = birth;
    }

    public Dog() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Dog [name=" + name + ", birth=" + birth + "]";
    }
}
