package com.example.jdt.s3;

import java.io.Serial;
import java.io.Serializable;

public class WrongSerialDog implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    private final String name;
    private Tail tail;

    public WrongSerialDog(String name) {
        this.name = name;
        this.tail = new Tail();
    }

    public void bark() {
        System.out.println(name + " barks!");
    }

    public void wag() {
        tail.wag();
    }
}
