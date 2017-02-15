package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.db.entity.MyScore;
import me.ianhe.manager.ScoreManager;
import me.ianhe.model.Result;
import me.ianhe.utils.AutoSendMail;
import me.ianhe.utils.JSON;
import me.ianhe.utils.ResponseUtil;
import me.ianhe.utils.TemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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

    public static final String COMMOMDATE = "yyyy-MM-dd";
    protected static final int PAGE_LENGTH = 10;

    @RequestMapping(value = "addScore")
    public void addScore(HttpServletResponse response, int score, String reason) throws UnsupportedEncodingException {
        MyScore ms = new MyScore();
        ms.setScore(score);
        ms.setReason(reason);
        ms.setAddWriter(1);
        ms.setAddDate(new Date());
        myScoreManager.addRecord(ms);
        ResponseUtil.writeSuccessJSON(response);
        Map<String, Object> res = Maps.newHashMap();
        res.put("score", ms);
        res.put("total", myScoreManager.getMyTotalScore());
        String mailContent = TemplateUtil.applyTemplateSimple("/mail/score.ftl", res);
        new AutoSendMail("ahaqhelin@163.com", "何霖", "加分提醒:今天加了" + score + "分", mailContent).start();
        new AutoSendMail("1018954240@qq.com", "葫芦娃", "加分提醒:今天加了" + score + "分", mailContent).start();
    }

    @ResponseBody
    @RequestMapping(value = "getMyScore")
    public String getTotalScore() {
        Result rs = new Result();
        int totalScore = myScoreManager.getMyTotalScore();
        rs.setData(JSON.toJson(totalScore));
        rs.setStatus(Result.SUCCESS_STATUS);
        return JSON.toJson(rs);
    }

    @ResponseBody
    @RequestMapping(value = "getDetail", method = RequestMethod.GET)
    public String getDetail(Integer id) {
        Result rs = new Result();
        MyScore myScore = myScoreManager.getById(id);
        rs.setData(JSON.toJson(myScore));
        rs.setStatus(Result.SUCCESS_STATUS);
        return JSON.toJson(rs);
    }

    @ResponseBody
    @RequestMapping(value = "getScoreHistory")
    public String getScoreHistory(Integer pageNum) {
        if (pageNum == null)
            pageNum = 1;
        Result rs = new Result();
        List<MyScore> scores = myScoreManager.selectByCondition((pageNum - 1) * PAGE_LENGTH, PAGE_LENGTH);
        rs.setData(JSON.toJson(scores));
        rs.setStatus(Result.SUCCESS_STATUS);
        String res = JSON.toJson(rs);
        return res;
    }

}
