package com.mycompany.pesel.jobs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalTime;

public class BreaksJobTest {
    BreaksJob breaksJob;
    FastList<LocalTime> breakTimes = FastList.newList(0);
    FastList<LocalTime> classTimes = FastList.newList(0);

    @Before
    public void setup() {
        breaksJob = new BreaksJob();

        /* Characteristic points for breaks and classes */
        breakTimes.add(LocalTime.of(8, 14));
        breakTimes.add(LocalTime.of(9, 46));
        breakTimes.add(LocalTime.of(9, 59));
        breakTimes.add(LocalTime.of(11, 31));
        breakTimes.add(LocalTime.of(11, 44));
        breakTimes.add(LocalTime.of(13, 16));
        breakTimes.add(LocalTime.of(13, 44));
        breakTimes.add(LocalTime.of(15, 16));
        breakTimes.add(LocalTime.of(15, 29));
        breakTimes.add(LocalTime.of(17, 01));
        breakTimes.add(LocalTime.of(17, 14));
        breakTimes.add(LocalTime.of(18, 46));

        classTimes.add(LocalTime.of(8, 15));
        classTimes.add(LocalTime.of(9, 45));
        classTimes.add(LocalTime.of(10, 00));
        classTimes.add(LocalTime.of(11, 30));
        classTimes.add(LocalTime.of(11, 45));
        classTimes.add(LocalTime.of(13, 15));
        classTimes.add(LocalTime.of(13, 45));
        classTimes.add(LocalTime.of(15, 15));
        classTimes.add(LocalTime.of(15, 30));
        classTimes.add(LocalTime.of(17, 00));
        classTimes.add(LocalTime.of(17, 15));
        classTimes.add(LocalTime.of(18, 45));
    }

    @Test
    public void shouldBeBreak() {
        breakTimes.forEach(breakTime ->
                assertTrue(breakTime + " " + breaksJob.isItBreakNow(breaksJob.schedule, breakTime),
                        breaksJob.isItBreakNow(breaksJob.schedule, breakTime)));
        System.out.println("elo");
    }

    @Test
    public void shouldBeClass() {
        classTimes.forEach(classTime ->
                assertFalse(classTime + " " + breaksJob.isItBreakNow(breaksJob.schedule, classTime),
                        breaksJob.isItBreakNow(breaksJob.schedule, classTime)));
    }
}
