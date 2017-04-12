package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.db.entity.MyScore;
import me.ianhe.model.AutoSendMail;
import me.ianhe.utils.DingUtil;
import me.ianhe.utils.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分数
 *
 * @author iHelin
 * @create 2017-02-15 19:18
 */
@Controller
public class ScoreController extends BaseController {

    /**
     * 加分操作
     *
     * @param myScore
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "score", method = RequestMethod.POST)
    public String addScore(MyScore myScore) {
        myScore.setAddDate(new Date());
        myScoreManager.addRecord(myScore);
        Map<String, Object> res = Maps.newHashMap();
        res.put("score", myScore);
        int total = myScoreManager.getMyTotalScore();
        res.put("total", total);
        String mailContent = TemplateUtil.applyTemplate("/tpl/score.ftl", res);
        AutoSendMail m1 = new AutoSendMail("ahaqhelin@163.com", "何霖", "加分提醒:今天加了" + myScore.getScore() + "分", mailContent);
        AutoSendMail m2 = new AutoSendMail("1018954240@qq.com", "葫芦娃", "加分提醒:今天加了" + myScore.getScore() + "分", mailContent);
        Thread t1 = new Thread(m1);
        Thread t2 = new Thread(m2);
        t1.start();
        t2.start();
        DingUtil.say("今天又加了" + myScore.getScore() + "分，理由是：" + myScore.getReason() + "，现在一共有"
                + total + "分，加油，你们要继续努力呦！");
        return success();
    }

    @ResponseBody
    @RequestMapping(value = "score/all", method = RequestMethod.GET)
    public String getTotalScore() {
        int totalScore = myScoreManager.getMyTotalScore();
        return success(totalScore);
    }

    @ResponseBody
    @RequestMapping(value = "score/{id}", method = RequestMethod.GET)
    public String getScore(@PathVariable Integer id) {
        MyScore myScore = myScoreManager.getById(id);
        return success(myScore);
    }

    @ResponseBody
    @RequestMapping(value = "scores", method = RequestMethod.GET)
    public String getScores(Integer pageNum, Integer pageLength) {
        if (pageNum == null)
            pageNum = 1;
        if (pageLength == null) {
            pageLength = 10;
        }
        List<MyScore> scores = myScoreManager.selectByCondition((pageNum - 1) * pageLength, pageLength);
        return success(scores);
    }

}
