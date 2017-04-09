package me.ianhe.controller;

import me.ianhe.db.entity.ServiceMenu;
import me.ianhe.db.entity.User;
import me.ianhe.model.WXUser;
import me.ianhe.model.req.LocationMessage;
import me.ianhe.utils.CheckUtil;
import me.ianhe.utils.DingUtil;
import me.ianhe.utils.JSON;
import me.ianhe.utils.WechatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信消息接入
 * Created by iHelin on 16/11/4.
 */
@Controller
public class AccessWeChatController extends BaseController {

    private static final String GIT_COMMIT_URL = "https://api.github.com/repos/iHelin/iHelin/commits?per_page=1";

    /**
     * get消息：消息服务器验证
     */
    @GetMapping(value = "access_wechat")
    public void doGet(String signature, String timestamp, String nonce, String echostr,
                      HttpServletResponse response) throws IOException {
        logger.info("验证token");
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            logger.info("验证成功");
            response.getWriter().print(echostr);
        } else {
            logger.info("验证失败，echostr：" + echostr);
        }
    }

    /**
     * post消息：消息处理
     */
    @ResponseBody
    @PostMapping(value = "access_wechat")
    public String doPost(HttpServletRequest request) {
        Map<String, String> msgMap = WechatUtil.xml2Map(request);
        String msgType = msgMap.get("MsgType");
        String respMessage;
        if (WechatUtil.MESSAGE_EVNET.equals(msgType)) {
            respMessage = processEvent(msgMap); // 进入事件处理
        } else {
            respMessage = processMessage(msgMap); // 进入普通消息处理
        }
        System.out.println(respMessage);
        return respMessage;
    }

    /**
     * 普通消息处理
     */
    public String processMessage(Map<String, String> msgMap) {
        String message;
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");
        String msgType = msgMap.get("MsgType");
        String content = msgMap.get("Content");
        System.out.println("消息类型：" + msgType);
        switch (msgType) {
            case WechatUtil.MESSAGE_TEXT:
                // 处理文本消息
                message = textMessage(content, toUserName, fromUserName);
                break;
            case WechatUtil.MESSAGE_IMAGE:
                // 图片消息处理
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的图片！");
                break;
            case WechatUtil.MESSAGE_LOCATION:
                // 处理地理位置消息
                LocationMessage locationMsg = WechatUtil.MapToLocation(msgMap);
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, locationMsg.getLabel());
                break;
            case WechatUtil.MESSAGE_LINK:
                // 链接消息
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的链接！");
                break;
            case WechatUtil.MESSAGE_VIDEO:
                // 视频消息
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的视频！");
                break;
            case WechatUtil.MESSAGE_VOICE:
                // 音频消息
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, "我已经收到了你的音频！");
                break;
            default:
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, "未知的消息。");
                break;
        }
        return message;
    }

    /**
     * 事件类消息处理
     *
     * @param msgMap
     * @return
     */
    public String processEvent(Map<String, String> msgMap) {
        System.out.println(msgMap);
        String eventType = msgMap.get("Event");
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");
        String message = "";
        if (WechatUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
            message = WechatUtil.sendTextMsg(toUserName, fromUserName, "感谢您的关注，我会继续努力！");// 关注事件
        } else if (WechatUtil.MESSAGE_VIEW.equals(eventType)) {
            // view类型事件，访问网页
            String url = msgMap.get("EventKey");
            logger.info("用户正在访问：{}", url);
        } else if (WechatUtil.MESSAGE_SCANCODE.equals(eventType)) {
            // 扫码事件
            String key = msgMap.get("EventKey");
            message = WechatUtil.sendTextMsg(toUserName, fromUserName, key);
        } else if (WechatUtil.MESSAGE_CLICK.equals(eventType)) {
            String key = msgMap.get("EventKey");
            ServiceMenu serviceMenu = serviceMenuMannger.getMenuById(Integer.valueOf(key));
            message = WechatUtil.sendTextMsg(toUserName, fromUserName, serviceMenu.getContent());
        }
        return message;
    }

    /**
     * 文本消息处理
     */
    public String textMessage(String content, String toUserName, String fromUserName) {
        User user = userManager.selectUserByOpenId(fromUserName);
        if (user == null) {
            String accessToken = accessTokenManager.getAccessToken().getToken();
            WXUser wxUser = userManager.selectWXUserByOpenId(fromUserName, accessToken);
            user = userManager.transWXUserToUser(wxUser);
            userManager.insertUser(user);
        }
        DingUtil.say("用户" + user.getNickName() + "发来消息：" + content);
        String message;
        switch (content) {
            case "1":
                message = WechatUtil.sendTextMsg(toUserName, fromUserName,
                        "你想干嘛？");
                break;
            case "2":
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, "你好，" + user.getNickName());
                break;
            case "commit":
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, getGitCommitUrl());
                break;
            default:
                message = WechatUtil.sendTextMsg(toUserName, fromUserName, "你好，" + user.getNickName());
                break;
        }
        return message;
    }

    public static String getGitCommitUrl() {
        StringBuilder sb = new StringBuilder();
        String res = WechatUtil.doGetStr(GIT_COMMIT_URL);
        List<HashMap> listMap = JSON.parseArrayMap(res);
        Map<String, Object> commit = (Map<String, Object>) listMap.get(0).get("commit");
        sb.append("最后提交：" + commit.get("message") + "\n");
        Map author = (Map) commit.get("author");
        sb.append("提交时间：" + author.get("date") + "\n");
        sb.append("作者：" + author.get("name"));
        return sb.toString();
    }

}
