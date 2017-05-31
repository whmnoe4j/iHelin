package me.ianhe.advice;

import me.ianhe.db.entity.MyScore;
import me.ianhe.service.ScoreService;
import me.ianhe.utils.DingUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author iHelin
 * @since 2017/5/31 16:51
 */
@Aspect
public class ScoreAfterAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScoreService scoreService;

    @AfterReturning(value = "execution(* addRecord(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        logger.debug("start...");
        MyScore myScore = (MyScore) joinPoint.getArgs()[0];
        long total = scoreService.getMyTotalScore();
        DingUtil.say("今天又加了" + myScore.getScore() + "分，理由是：" + myScore.getReason() + "，现在一共有"
                + total + "分，加油，你们要继续努力呦！");
    }

}
