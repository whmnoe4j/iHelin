package me.ianhe.controller;

import me.ianhe.entity.MyScore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
    @PostMapping("score")
    public String addScore(MyScore myScore) {
        myScore.setAddDate(new Date());
        scoreService.addRecord(myScore);
        return success();
    }

    @GetMapping("score/all")
    public String getTotalScore() {
        long totalScore = scoreService.getMyTotalScore();
        return success(totalScore);
    }

    @GetMapping("score/{id}")
    public String getScore(@PathVariable Integer id) {
        MyScore myScore = scoreService.getById(id);
        return success(myScore);
    }

    @GetMapping("scores")
    public String getScores(Integer pageNum, Integer pageLength) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageLength = pageLength == null ? DEFAULT_PAGE_LENGTH : pageLength;
        List<MyScore> scores = scoreService.selectByCondition(pageNum, pageLength);
        return success(scores);
    }

}
