package me.ianhe.utils;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author iHelin
 * @since 2017/6/30 09:24
 */
public class LunarCalendarTest {
    @Test
    public void animalsYear() throws Exception {

    }

    @Test
    public void cyclical() throws Exception {
    }

    @Test
    public void getDate() throws Exception {
        LunarCalendar lunarCalendar = new LunarCalendar();
        System.out.println(lunarCalendar);
        System.out.println(lunarCalendar.animalsYear());
        System.out.println(lunarCalendar.cyclical());
    }

    @Test
    public void getChinaDayString() throws Exception {
    }

}