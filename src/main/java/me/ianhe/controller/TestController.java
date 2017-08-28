package me.ianhe.controller;

import me.ianhe.db.entity.MyScore;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class TestController extends BaseController {

    @GetMapping("test")
    public String testPage(@RequestHeader("User-Agent") String userAgent) {
        System.out.println(userAgent);
        return "test";
    }

    @ResponseBody
    @GetMapping("test2")
    public MyScore test2() {
        MyScore myScore = new MyScore();
        myScore.setAddDate(new Date());
        myScore.setAddWriter(1);
        myScore.setReason("<h1>三个人请问abc123</h1>");
        myScore.setScore(1);
        return myScore;
    }

    /**
     * 文件下载测试
     *
     * @author iHelin
     * @create 2017-04-26 14:12
     */
    @ResponseBody
    @RequestMapping("download")
    public ResponseEntity<String> download() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "test.txt");
        return new ResponseEntity("download test", headers, HttpStatus.CREATED);
    }

    @GetMapping("dateTest")
    @ResponseBody
    public String dateTest(Date date) {
        System.out.println(date);
        return date.toString();
    }

}
