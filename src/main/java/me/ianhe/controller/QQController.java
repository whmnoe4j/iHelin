package me.ianhe.controller;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.WQQClient;
import com.thankjava.wqq.consts.MsgType;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.GroupInfo;
import com.thankjava.wqq.entity.wqq.GroupsList;
import me.ianhe.config.QQNotifyListener;
import me.ianhe.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/9/21 10:01
 */
@Controller
public class QQController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QQNotifyListener listener;

    private SmartQQClient smartQQClient;

    @PostConstruct
    private void init() {
        smartQQClient = new WQQClient(false, 3, listener);
        listener.setSmartQQClient(smartQQClient);
    }

    @GetMapping("qq")
    public String qqLogin(HttpServletRequest request) {
        logger.debug("smart-qq");
        String savePath = request.getServletContext().getRealPath("/img");
        File file = new File(savePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        System.out.println(savePath);
        smartQQClient.login(true, listenerAction -> {
            try {
                OutputStream os = new FileOutputStream(savePath + "/tmp.png");
                ImageIO.write((BufferedImage) listenerAction.getData(), "png", os);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, listenerAction -> logger.debug("登录成功"));
        return "test";
    }

    @ResponseBody
    @GetMapping("send")
    public String send(String msg) {
        logger.debug("消息：" + msg);
        GroupsList groupsList = smartQQClient.getGroupsList(true);
        logger.debug(JSON.toJson(groupsList.getGroups()));
        Map<Long, GroupInfo> groupInfoMap = groupsList.getGroups();
        for (Map.Entry<Long, GroupInfo> groupInfoEntry : groupInfoMap.entrySet()) {
            if (groupInfoEntry.getValue().getName().equals("一点点学习小组")) {
                long gid = groupInfoEntry.getKey();
                SendMsg sendMsg = new SendMsg(gid, MsgType.group_message, msg);
                smartQQClient.sendMsg(sendMsg);
                logger.debug("发送一条群消息");
            }
        }
        return success();
    }

}
