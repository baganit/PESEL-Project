package com.mycompany.pesel.main;

import com.mycompany.pesel.jobs.BreaksJob;
import com.mycompany.pesel.jobs.ResultsJob;
import org.apache.log4j.Logger;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import java.util.Scanner;

public class App
{
    static FastListMultimap<String, Person> people = FastListMultimap.newMultimap();
    static Logger log = Logger.getLogger(App.class);
    static Scanner reader = new Scanner(System.in);
    static Scheduler scheduler;
    static {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) throws SchedulerException {
        initializeResultsJob();
        initializeBreaksJob();

        while (true) {
            System.out.println("Name: ");
            String name = reader.next();

            if(name.equals("!q"))
                exitAndCleanUp();

            System.out.println("Surname: ");
            String surname = reader.next();

            System.out.println("City: ");
            String city = reader.next();

            System.out.println("PESEL: ");
            String pesel = reader.next();
            System.out.println(pesel);


            Person person = new Person(name, surname, city, pesel);

            if (peselValid(pesel)) {
                people.put(person.getCity(), person);
                System.out.println("o ja");
                System.out.println(people);

            } else
                throw new IllegalArgumentException("The entered PESEL number is incorrect. Saving failed");
        }
    }

    private static int getValue(String text, int index) {
        return Character.getNumericValue(text.charAt(index));
    }

    private static boolean peselValid(String pesel) {
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

    private static void initializeResultsJob() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail resultsJob = newJob(ResultsJob.class)
                    .withIdentity("results", "group1")
                    .build();

            Trigger resultsTrigger = newTrigger()
                    .withIdentity("resultsTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(30)
                    .repeatForever())
                    .build();

            scheduler.scheduleJob(resultsJob, resultsTrigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static void initializeBreaksJob() {
        try {
            scheduler.getContext().put("people", people);
            scheduler.start();

            JobDetail breaksJob = newJob(BreaksJob.class)
                    .withIdentity("breaks", "group1")
                    .build();

            Trigger breaksTrigger = newTrigger()
                    .withIdentity("breaksTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(breaksJob, breaksTrigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static void exitAndCleanUp() throws SchedulerException {
        reader.close();
        scheduler.shutdown();
        System.exit(0);
    }
}
