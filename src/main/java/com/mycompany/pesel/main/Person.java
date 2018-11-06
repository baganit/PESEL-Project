package com.mycompany.pesel.main;

public class Person {
    String name;
    String surname;
    String city;
    String pesel;

    public Person(String name, String surname, String city, String pesel) {
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCity() {
        return city;
    }

    public String getPesel() {
        return pesel;
    }
}
