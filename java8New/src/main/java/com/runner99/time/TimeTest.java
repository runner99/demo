package com.runner99.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeTest {

    @Test
    public void tets01(){
        long l = System.currentTimeMillis();
        System.out.println(l);
        LocalDate now = LocalDate.now();
        System.out.println(now);
        LocalTime now2 = LocalTime.now();
        System.out.println(now2.toString());
        LocalDateTime now1 = LocalDateTime.now();
        System.out.println(now1);
    }
}
