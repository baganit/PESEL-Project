package com.mycompany.pesel;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;

import java.io.IOException;
import java.util.Scanner;

public class App 
{
    static Logger log = Logger.getLogger(App.class);

    public static void main( String[] args ) throws IOException {
        Scanner reader = new Scanner(System.in);

        System.out.println("Name: ");
        String name = reader.next();

        System.out.println("Surname: ");
        String surname = reader.next();

        System.out.println("PESEL: ");
        String pesel = reader.next();

        reader.close();

        Person person = new Person(name, surname, pesel);

        if(peselValid(pesel)) {
            PatternLayout layout = new PatternLayout();
            FileAppender appender = new FileAppender(layout, "./logs/" + pesel,false);
            log.addAppender(appender);
            log.info("Name: " + person.getName() + "\n" +
                    "Surname: " + person.getSurname() + "\n" +
                    "PESEL: " + person.getPesel());
        }
        else
            throw new IllegalArgumentException("The entered PESEL number is incorrect. Saving failed");
    }

    private static boolean peselValid(String pesel) {
        int sum;
        if(pesel.length() == 11) {
            sum = getValue(pesel, 0) * 1 + getValue(pesel, 1) * 3 +
                    getValue(pesel, 2) * 7 + getValue(pesel, 3) * 9 +
                    getValue(pesel, 4) * 1 + getValue(pesel, 5) * 3 +
                    getValue(pesel, 6) * 7 + getValue(pesel, 7) * 9 +
                    getValue(pesel, 8) * 1 + getValue(pesel, 9) * 3 +
                    getValue(pesel, 10) * 1;

            if(sum % 10 != 0)
                return false;

            return true;
        }
        else
            return false;
    }

    private static int getValue(String text, int index) {
        return Character.getNumericValue(text.charAt(index));
    }
}
