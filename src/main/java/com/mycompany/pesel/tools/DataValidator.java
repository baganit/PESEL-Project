package com.mycompany.pesel.tools;

import com.mycompany.pesel.main.Person;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;

public class DataValidator {

    public static boolean inputDataValid(String data) {
        String[] splitData = data.split("\\s+");
        if (splitData.length == 3) {
            int digitsOnlyCounter = 0;
            int lettersOnlyCounter = 0;

            for (int i = 0; i < splitData.length; i++) {
                if (splitData[i].matches("[0-9]+"))
                    digitsOnlyCounter++;
                else if (splitData[i].matches("[a-zA-Z]+"))
                    lettersOnlyCounter++;
            }
            if (digitsOnlyCounter == 1 && lettersOnlyCounter == 2)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public static boolean peselValid(String pesel) {
        int sum;
        if (pesel.length() == 11) {
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

    public static void removeExistingPesel(FastListMultimap<String, Person> people, String pesel) {
        people.forEachKey(city -> {
            people.forEachValue(person -> {
                if(person.getPesel().equals(pesel))
                    people.remove(city, person);
            });
        });
    }
}

