package com.mycompany.pesel.main;

import com.mycompany.pesel.jobs.BreaksJob;
import com.mycompany.pesel.jobs.ResultsJob;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import java.io.IOException;
import java.util.Scanner;

public class App
{
    static Logger log = Logger.getLogger(App.class);

    public static void main( String[] args ) throws IOException {
        initializeResultsJob();
        initializeBreaksJob();

        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.println("Name: ");
            String name = reader.next();

            System.out.println("Surname: ");
            String surname = reader.next();

            System.out.println("City: ");
            String city = reader.next();

            System.out.println("PESEL: ");
            String pesel = reader.next();


            Person person = new Person(name, surname, city, pesel);

            if (peselValid(pesel)) {
                PatternLayout layout = new PatternLayout();
                FileAppender appender = new FileAppender(layout, "./logs/" + pesel + ".log", false);
                log.addAppender(appender);
                log.info(person.getCity() + "\n" + person.getName() + " " + person.getSurname() + " " +
                        person.getPesel());
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
            //scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    private static void initializeBreaksJob() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
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
            //scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
