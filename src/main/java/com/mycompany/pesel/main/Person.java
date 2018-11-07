package com.mycompany.pesel.main;

import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;

public class Person {
    String name;
    String city;
    String pesel;

    public Person(String name, String city, String pesel) {
        this.name = name;
        this.city = city;
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPesel() {
        return pesel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
