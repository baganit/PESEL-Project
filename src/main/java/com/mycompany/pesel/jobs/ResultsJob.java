package com.mycompany.pesel.jobs;

import com.mycompany.pesel.main.Person;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.quartz.*;

import java.io.File;
import java.io.IOException;

//@PersistJobDataAfterExecution
public class ResultsJob implements org.quartz.Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Jazdaa");

        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = jobExecutionContext.getScheduler().getContext();
        } catch (SchedulerException e1) {
            e1.printStackTrace();
        }

        FastListMultimap<String, Person> citiesToPeople =
                (FastListMultimap<String, Person>) schedulerContext.get("people");

        File results = new File("./logs/results.log");
        results.delete();

        Logger log = Logger.getLogger(ResultsJob.class);
        PatternLayout layout = new PatternLayout();
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, "./logs/results.log", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.addAppender(appender);

        citiesToPeople.forEachKey(city -> {
            log.info(city + ":\n******");
            citiesToPeople.get(city).forEach(person ->
                        log.info(person.getName() + " " + person.getPesel())
            );
            log.info("");
        });
    }
}

