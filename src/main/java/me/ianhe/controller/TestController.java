package me.ianhe.controller;

import me.ianhe.db.entity.MyScore;
import me.ianhe.model.MailModel;
import me.ianhe.service.JMSProducerService;
import me.ianhe.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Destination;
import java.util.Date;

@Controller
public class TestController extends BaseController {

    @Autowired
    private JMSProducerService producerService;

    @Autowired
    @Qualifier("mailQueue")
    private Destination destination;

    @GetMapping("test/agent")
    public String testPage(@RequestHeader("User-Agent") String userAgent) {
        System.out.println(userAgent);
        MailModel mail = new MailModel("ahaqhelin@163.com", "葫芦娃", "2345", "测试服二个人");
        producerService.sendMessage(destination, mail);
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
        System.out.println(Global.getSystemName());
        return myScore;
    }

    /**
     * 文件下载测试
     *
     * @author iHelin
     * @since 2017-04-26 14:12
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
