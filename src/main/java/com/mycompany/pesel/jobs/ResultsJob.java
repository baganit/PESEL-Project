package com.mycompany.pesel.jobs;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class ResultsJob implements org.quartz.Job  {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Jazdaa");
        FastListMultimap<String, String> citiesToPeople = FastListMultimap.newMultimap();
        File[] logfiles = new File("./logs/").listFiles();
        File results = new File("./logs/results.log");

        results.delete();

        for(File file: logfiles) {
            if(!file.equals(results)) {
                List<String> data = loadDataFromLogfile(file);
                citiesToPeople.put(data.get(0), data.get(1));
            }
        }
        Logger log = Logger.getLogger(ResultsJob.class);

        PatternLayout layout = new PatternLayout();
        FileAppender appender = null;
        try {
            appender = new FileAppender(layout, "./logs/results.log",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.addAppender(appender);
        citiesToPeople.forEachKey(city -> log.info(city + ":\n" + citiesToPeople.get(city) + "\n"));
    }

    private List<String> loadDataFromLogfile(File file) {

        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}

