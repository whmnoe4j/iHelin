package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.db.entity.MyScore;
import me.ianhe.model.AutoSendMail;
import me.ianhe.utils.DingUtil;
import me.ianhe.utils.TemplateUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 分数
 *
 * @author iHelin
 * @create 2017-02-15 19:18
 */
@RestController
public class ScoreController extends BaseController {

    /**
     * 加分操作
     *
     * @param myScore
     * @return
     */
    @RequestMapping(value = "score", method = RequestMethod.POST)
    public String addScore(MyScore myScore) {
        myScore.setAddDate(new Date());
        myScoreManager.addRecord(myScore);
        return success();
    }

    @RequestMapping(value = "score/all", method = RequestMethod.GET)
    public String getTotalScore() {
        long totalScore = myScoreManager.getMyTotalScore();
        return success(totalScore);
    }

    @RequestMapping(value = "score/{id}", method = RequestMethod.GET)
    public String getScore(@PathVariable Integer id) {
        MyScore myScore = myScoreManager.getById(id);
        return success(myScore);
    }

    @GetMapping("scores")
    public String getScores(Integer pageNum, Integer pageLength) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageLength = pageLength == null ? DEFAULT_PAGE_LENGTH : pageLength;
        List<MyScore> scores = myScoreManager.selectByCondition((pageNum - 1) * pageLength, pageLength);
        return success(scores);
    }

}
