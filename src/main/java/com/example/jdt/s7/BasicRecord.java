package com.example.jdt.s7;

record Simple(String s) {
}

// this won't compile, records are final
//record Wrong(int value) extends Simple {
//}

public class BasicRecord {
    public static void main(String[] args) {
        Simple simple = new Simple("hello");

        System.out.println("A record as a string: " + simple);
        System.out.println("Accessing a record property: " + simple.s());
        System.out.println("A record hash code: " + simple.hashCode());

        Simple alias = simple;
        System.out.println("Two references to the same record are equal: " + simple.equals(alias));

        Simple same = new Simple("hello");
        System.out.println("Two different records are equal if their properies are equal: " + simple.equals(same));

        Simple other = new Simple("goodbye");
        System.out.println("Comparing two different records: " + simple.equals(other));

        System.out.println("Comparing a record against null: " + simple.equals(null));

        Simple unset = new Simple(null);
        System.out.println("Comparing to a record with null in it: " + simple.equals(unset));
    }
}
