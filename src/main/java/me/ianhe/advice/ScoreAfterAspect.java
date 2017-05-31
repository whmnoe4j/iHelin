package me.ianhe.advice;

import com.beust.jcommander.internal.Maps;
import me.ianhe.db.entity.MyScore;
import me.ianhe.model.AutoSendMail;
import me.ianhe.service.ScoreService;
import me.ianhe.utils.DingUtil;
import me.ianhe.utils.TemplateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 加分提醒
 *
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

        Map<String, Object> res = Maps.newHashMap();
        res.put("score", myScore);
        res.put("total", total);
        String mailContent = TemplateUtil.applyTemplate("score.ftl", res);
        String title = "加分提醒:今天加了" + myScore.getScore() + "分";
        AutoSendMail m1 = new AutoSendMail("ahaqhelin@163.com", "何霖", title, mailContent);
        AutoSendMail m2 = new AutoSendMail("1018954240@qq.com", "葫芦娃", title, mailContent);
        Thread t1 = new Thread(m1);
        Thread t2 = new Thread(m2);
        t1.start();
        t2.start();
    }

}
