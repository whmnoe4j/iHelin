package me.ianhe.service;

import me.ianhe.utils.DingUtil;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author iHelin
 * @since 2017/8/9 09:19
 */
@Service
public class CountDownService {

    public void run() {
        Calendar examDate = new Calendar.Builder().setDate(2017, Calendar.OCTOBER, 14)
                .build();
        long examDateLong = examDate.getTimeInMillis();
        long nowLong = System.currentTimeMillis();
        long betweenDays = (examDateLong - nowLong) / (1000L * 3600 * 24) + 1;
        if (betweenDays > 0) {
            DingUtil.say("今天距离考试还剩" + betweenDays + "天！");
        }
    }

}
