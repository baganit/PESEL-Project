package com.mycompany.pesel.main;

import com.mycompany.pesel.tools.DataParser;
import com.mycompany.pesel.tools.DataValidator;
import com.mycompany.pesel.tools.JobInitializer;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Scanner;

public class App {
    static FastListMultimap<String, Person> people = FastListMultimap.newMultimap();
    static Scanner reader = new Scanner(System.in);
    static Scheduler scheduler;
    static {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    private static String name;
    private static String pesel;


    public static void main( String[] args ) throws SchedulerException {
        JobInitializer.initializeJobs(scheduler, people);

        while (true) {
            System.out.println("City: ");
            String city = reader.next();
            isItQuit(city);

            System.out.println("Data: ");
            reader.useDelimiter("\\n");
            String data = reader.next();
            isItQuit(data);

            if (DataValidator.inputDataValid(data)) {
                DataParser parser = new DataParser(data);
                parser.parseData();
                name = parser.getName();
                pesel = parser.getPesel();
            }
            else
                throw new IllegalArgumentException("Entered data is incorrect!");

            Person person = new Person(name, city, pesel);

            if (DataValidator.peselValid(pesel)) {
                DataValidator.removeExistingPesel(people, pesel);
                people.put(city, person);
            }
            else
                throw new IllegalArgumentException("The entered PESEL number is incorrect. Saving failed");

        }
    }

    private static void isItQuit(String text) throws SchedulerException {
        if (text.equals("!q"))
            exitAndCleanUp();
    }

    private static void exitAndCleanUp() throws SchedulerException {
        reader.close();
        scheduler.shutdown();
        System.exit(0);
    }
}
