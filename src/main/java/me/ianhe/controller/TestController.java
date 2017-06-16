package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.db.entity.MyScore;
import me.ianhe.utils.JSON;
import me.ianhe.utils.MailUtil;
import me.ianhe.utils.ResponseUtil;
import me.ianhe.utils.TemplateUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Controller
public class TestController extends BaseController {

    /**
     * 测试从模板中获取内容并发送邮件
     *
     * @author iHelin
     * @create 2017-04-30 08:45
     */
    @ResponseBody
    @GetMapping(value = "tpl")
    public String mail() {
        String template = TemplateUtil.applyTemplate("mail_content.ftl");
        MailUtil.sendMail("ihelin@outlook.com", "iHelin", "哈哈", template);
        return template;
    }

    /**
     * aop测试
     *
     * @author iHelin
     * @since 2017/5/31 16:26
     */
    @GetMapping("aop")
    @ResponseBody
    public String testAop() {
        long total = myScoreManager.getMyTotalScore();
        return String.valueOf(total);
    }

    @ResponseBody
    @PostMapping("test_post")
    public String testPost(@RequestBody String hello) {
        logger.debug("get:{}", hello);
        return "hahahhahahhah";
    }

    @ResponseBody
    @RequestMapping(value = "console", method = RequestMethod.GET)
    public String getProperties() {
        Properties props = System.getProperties();
        return JSON.toJson(props);
    }

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        return success("<h1>三个人请问abc123</h1>");
    }

    @GetMapping(value = "test1")
    public void test1(HttpServletResponse response, HttpSession session) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("data", "<h1>三个人请前雾灯无问abc123</h1>");
        ResponseUtil.writeSuccessJSON(response, data);
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

}
