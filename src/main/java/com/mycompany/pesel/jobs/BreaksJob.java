package com.mycompany.pesel.jobs;

import org.eclipse.collections.impl.list.mutable.FastList;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BreaksJob implements org.quartz.Job {
    FastList<LocalTime> schedule = initializeSchedule();

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalTime now = LocalTime.now();
        long minutes = checkMinutesTillEnd(schedule, now);

        if (isItBreakNow(schedule, now))
            System.out.println(minutes + " minutes to the end of break.");
        else
            System.out.println(minutes + " minutes to the end of classes.");
    }


    boolean isItBreakNow(FastList<LocalTime> schedule, LocalTime now) {
        int index = 0;
        LocalTime endOfLastClasses = schedule.get(10);

        if (now.isAfter(endOfLastClasses))
            return true;

        for (int i = 0; i < schedule.size(); i++) {
            if (now.isBefore(schedule.get(i))) {
                index = i;
                break;
            }
        }

        if (index % 2 == 0)
            return false;
        else
            return true;
    }

    long checkMinutesTillEnd(FastList<LocalTime> schedule, LocalTime now) {
        long minutes = -1;
        LocalTime endOfLastClasses = schedule.get(10);
        LocalTime beginningOfFirstClasses = schedule.get(11);

        if (now.isAfter(endOfLastClasses)) {
            minutes = now.until(LocalTime.MAX, MINUTES)
                    + LocalTime.MIN.until(beginningOfFirstClasses, MINUTES);
        }
        else {
            for (int i = 0; i < schedule.size(); i++) {
                LocalTime currentEnd = schedule.get(i);

                if (now.isBefore(currentEnd)) {
                    minutes = now.until(currentEnd, MINUTES);
                    break;
                }
            }
        }

        return minutes;
    }

    private FastList<LocalTime> initializeSchedule() {
        FastList<LocalTime> schedule = FastList.newList(0);

        LocalTime endOfClass1 = LocalTime.of(9, 45);
        LocalTime endOfBreak1 = LocalTime.of(10, 00);
        LocalTime endOfClass2 = LocalTime.of(11, 30);
        LocalTime endOfBreak2 = LocalTime.of(11, 45);
        LocalTime endOfClass3 = LocalTime.of(13, 15);
        LocalTime endOfBreak3 = LocalTime.of(13,45);
        LocalTime endOfClass4 = LocalTime.of(15,15);
        LocalTime endOfBreak4 = LocalTime.of(15, 30);
        LocalTime endOfClass5 = LocalTime.of(17,00);
        LocalTime endOfBreak5 = LocalTime.of(17, 15);
        LocalTime endOfClass6 = LocalTime.of(18, 45);
        LocalTime endOfBreak6 = LocalTime.of(8, 15);

        schedule.add(endOfClass1);
        schedule.add(endOfBreak1);
        schedule.add(endOfClass2);
        schedule.add(endOfBreak2);
        schedule.add(endOfClass3);
        schedule.add(endOfBreak3);
        schedule.add(endOfClass4);
        schedule.add(endOfBreak4);
        schedule.add(endOfClass5);
        schedule.add(endOfBreak5);
        schedule.add(endOfClass6);
        schedule.add(endOfBreak6);

        return schedule;
    }
}
