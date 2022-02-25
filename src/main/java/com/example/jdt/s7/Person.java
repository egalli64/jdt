package com.example.jdt.s7;

import java.io.Serializable;

public record Person(String firstName, String lastName) implements Serializable {
}
