package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.db.entity.MyScore;
import me.ianhe.manager.ScoreManager;
import me.ianhe.utils.AutoSendMail;
import me.ianhe.utils.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
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

    @Autowired
    private ScoreManager myScoreManager;

    @ResponseBody
    @RequestMapping(value = "score", method = RequestMethod.POST)
    public String addScore(Integer score, String reason) throws UnsupportedEncodingException {
        MyScore ms = new MyScore();
        ms.setScore(score);
        ms.setReason(reason);
        ms.setAddWriter(1);
        ms.setAddDate(new Date());
        myScoreManager.addRecord(ms);
        Map<String, Object> res = Maps.newHashMap();
        res.put("score", ms);
        res.put("total", myScoreManager.getMyTotalScore());
        String mailContent = TemplateUtil.applyTemplateSimple("/mail/score.ftl", res);
        AutoSendMail m1 = new AutoSendMail("ahaqhelin@163.com", "何霖", "加分提醒:今天加了" + score + "分", mailContent);
        AutoSendMail m2 = new AutoSendMail("1018954240@qq.com", "葫芦娃", "加分提醒:今天加了" + score + "分", mailContent);
        Thread t1 = new Thread(m1);
        Thread t2 = new Thread(m2);
        t1.start();
        t2.start();
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
