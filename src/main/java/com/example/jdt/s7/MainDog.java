package com.example.jdt.s7;

public class MainDog {
    public static void main(String[] args) {
        Dog dog = new Dog();

        System.out.println("Dog name: " + dog.name());
        System.out.println("Dog birth: " + dog.birth());
        System.out.println("Dog: " + dog);

        System.out.println("Null data members disallowed for dog");
//        Dog x = new Dog(null, null);
//        System.out.println("Dog name: " + x.name());
//        System.out.println("Dog birth: " + x.birth());
//        System.out.println("Dog: " + dog);
    }
}
