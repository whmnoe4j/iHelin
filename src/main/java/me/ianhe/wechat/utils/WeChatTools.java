package me.ianhe.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.beans.RecommendInfo;
import me.ianhe.wechat.core.Core;
import me.ianhe.wechat.enums.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信小工具，如获好友列表等
 *
 * @author iHelin
 * @since 2017/12/11 14:33
 */
public class WeChatTools {

    private static Logger logger = LoggerFactory.getLogger(WeChatTools.class);
    private static final String API_WXAPPID = "API_WXAPPID";
    private static Core core = Core.getInstance();

    /**
     * 发送文本消息
     *
     * @param text
     * @param username
     * @author https://github.com/yaphone
     * @date 2017年5月6日 上午11:45:51
     */
    public static void sendTextMsgByUsername(String text, String username) {
        if (StringUtils.isEmpty(text)) {
            return;
        }
        logger.info("发送消息 {}: {}", username, text);
        sendTextMsg(MsgCodeEnum.MSGTYPE_TEXT, text, username);
    }

    /**
     * 处理下载任务
     *
     * @param msg
     * @param type
     */
    public static byte[] downloadMsg(BaseMsg msg, String type) {
        Map<String, String> headerMap = new HashMap<>();
        List<BasicNameValuePair> params = new ArrayList<>();
        String url = "";
        if (MsgTypeEnum.PIC.getType().equals(type)) {
            url = String.format(URLEnum.WEB_WX_GET_MSG_IMG.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (MsgTypeEnum.VOICE.getType().equals(type)) {
            url = String.format(URLEnum.WEB_WX_GET_VOICE.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (MsgTypeEnum.VIEDO.getType().equals(type)) {
            headerMap.put("Range", "bytes=0-");
            url = String.format(URLEnum.WEB_WX_GET_VIEDO.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (MsgTypeEnum.MEDIA.getType().equals(type)) {
            headerMap.put("Range", "bytes=0-");
            url = String.format(URLEnum.WEB_WX_GET_MEDIA.getUrl(), (String) core.getLoginInfo().get("fileUrl"));
            params.add(new BasicNameValuePair("sender", msg.getFromUserName()));
            params.add(new BasicNameValuePair("mediaid", msg.getMediaId()));
            params.add(new BasicNameValuePair("filename", msg.getFileName()));
        }
        params.add(new BasicNameValuePair("msgid", msg.getNewMsgId()));
        params.add(new BasicNameValuePair("skey", (String) core.getLoginInfo().get("skey")));
        HttpEntity entity = core.getMyHttpClient().doGet(url, params, true, headerMap);
        try {
            return EntityUtils.toByteArray(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据username发送图片消息
     *
     * @param username
     * @param filePath
     * @return
     * @author https://github.com/yaphone
     * @date 2017年5月7日 下午10:34:24
     */
    public static boolean sendPicMsgByUsername(String username, String filePath) {
        JSONObject responseObj = uploadMediaFile(filePath);
        if (responseObj != null) {
            String mediaId = responseObj.getString("MediaId");
            if (mediaId != null) {
                return sendImgMsg(username, mediaId);
            }
        }
        return false;
    }

    /**
     * 根据username发送文件
     *
     * @param username
     * @param filePath
     * @return
     */
    public static boolean sendFileMsgByUsername(String username, String filePath) {
        String title = new File(filePath).getName();
        Map<String, String> data = new HashMap<>();
        data.put("appid", API_WXAPPID);
        data.put("title", title);
        data.put("totallen", "");
        data.put("attachid", "");
        data.put("type", "6");
        // 文件后缀
        data.put("fileext", title.split("\\.")[1]);
        JSONObject responseObj = uploadMediaFile(filePath);
        if (responseObj != null) {
            data.put("totallen", responseObj.getString("StartPos"));
            data.put("attachid", responseObj.getString("MediaId"));
        } else {
            logger.error("sednFileMsgByUserId 错误: ", data);
        }
        return sendFileMsg(username, data);
    }

    /**
     * 接受添加好友请求
     *
     * @param msg
     * @param accept
     */
    public static void addFriend(BaseMsg msg, boolean accept) {
        if (!accept) {
            // 不添加
            return;
        }
        // 接受好友请求
        int status = VerifyFriendEnum.ACCEPT.getCode();
        RecommendInfo recommendInfo = msg.getRecommendInfo();
        String userName = recommendInfo.getUserName();
        String ticket = recommendInfo.getTicket();
        // 更新好友列表
        String url = String.format(URLEnum.WEB_WX_VERIFYUSER.getUrl(), core.getLoginInfo().get("url"),
                String.valueOf(System.currentTimeMillis() / 3158L), core.getLoginInfo().get("pass_ticket"));
        List<Map<String, Object>> verifyUserList = new ArrayList<>();
        Map<String, Object> verifyUser = new HashMap<>();
        verifyUser.put("Value", userName);
        verifyUser.put("VerifyUserTicket", ticket);
        verifyUserList.add(verifyUser);
        List<Integer> sceneList = new ArrayList<>();
        sceneList.add(33);
        JSONObject body = new JSONObject();
        body.put("BaseRequest", core.getBaseParamMap().get("BaseRequest"));
        body.put("Opcode", status);
        body.put("VerifyUserListSize", 1);
        body.put("VerifyUserList", verifyUserList);
        body.put("VerifyContent", "");
        body.put("SceneListCount", 1);
        body.put("SceneList", sceneList);
        body.put("skey", core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey()));
        String result = null;
        try {
            String paramStr = JSON.toJSONString(body);
            HttpEntity entity = core.getMyHttpClient().doPost(url, paramStr);
            result = EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            logger.error("sendTextMsg异常", e);
        }
        logger.debug(result);
    }

    /**
     * 通过nickName获取本次UserName
     *
     * @param nickName
     * @return
     */
    public static String getUserNameByNickName(String nickName) {
        for (JSONObject o : core.getContactList()) {
            if (o.getString("NickName").equals(nickName)) {
                return o.getString("UserName");
            }
        }
        return "";
    }

    /**
     * 返回好友昵称列表
     *
     * @return
     * @author https://github.com/yaphone
     * @date 2017年5月4日 下午11:37:20
     */
    public static List<String> getContactNickNameList() {
        List<String> contactNickNameList = new ArrayList<>();
        for (JSONObject o : getContactList()) {
            contactNickNameList.add(o.getString("NickName"));
        }
        return contactNickNameList;
    }

    /**
     * 返回好友完整信息列表
     *
     * @return
     * @date 2017年6月26日 下午9:45:39
     */
    public static List<JSONObject> getContactList() {
        return core.getContactList();
    }

    /**
     * 返回群列表
     *
     * @return
     * @author https://github.com/yaphone
     * @date 2017年5月5日 下午9:55:21
     */
    public static List<JSONObject> getGroupList() {
        return core.getGroupList();
    }

    /**
     * 获取群ID列表
     *
     * @return
     * @date 2017年6月21日 下午11:42:56
     */
    public static List<String> getGroupIdList() {
        return core.getGroupIdList();
    }

    /**
     * 获取群NickName列表
     *
     * @return
     * @date 2017年6月21日 下午11:43:38
     */
    public static List<String> getGroupNickNameList() {
        return core.getGroupNickNameList();
    }

    /**
     * 根据groupIdList返回群成员列表
     *
     * @param groupId
     * @return
     * @date 2017年6月13日 下午11:12:31
     */
    public static JSONArray getMemberListByGroupId(String groupId) {
        return core.getGroupMemeberMap().get(groupId);
    }

    /**
     * 消息发送
     *
     * @param msgCodeEnum
     * @param content
     * @param toUserName
     */
    private static void sendTextMsg(MsgCodeEnum msgCodeEnum, String content, String toUserName) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("Type", msgCodeEnum.getCode());
        msgMap.put("Content", content);
        msgMap.put("FromUserName", core.getUserName());
        msgMap.put("ToUserName", toUserName == null ? core.getUserName() : toUserName);
        msgMap.put("LocalID", System.currentTimeMillis() * 10);
        msgMap.put("ClientMsgId", System.currentTimeMillis() * 10);
        Map<String, Object> paramMap = core.getBaseParamMap();
        paramMap.put("Msg", msgMap);
        paramMap.put("Scene", 0);
        String url = String.format(URLEnum.WEB_WX_SEND_MSG.getUrl(), core.getLoginInfo().get("url"));
        try {
            String paramStr = JSON.toJSONString(paramMap);
            HttpEntity entity = core.getMyHttpClient().doPost(url, paramStr);
            String res = EntityUtils.toString(entity, Consts.UTF_8);
            logger.debug("发送消息的返回：{}", res);
        } catch (Exception e) {
            logger.error("发送文本消息异常", e);
        }
    }

    /**
     * 注销
     *
     * @return
     */
    public static void logout() {
        String url = String.format(URLEnum.WEB_WX_LOGOUT.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()));
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("redirect", "1"));
        params.add(new BasicNameValuePair("type", "1"));
        params.add(new BasicNameValuePair("skey",
                (String) core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey())));
        try {
            core.getMyHttpClient().doGet(url, params, false, null);
            core.setAlive(false);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    public static void setUserInfo() {
        for (JSONObject o : core.getContactList()) {
            core.getUserInfoMap().put(o.getString("NickName"), o);
            core.getUserInfoMap().put(o.getString("UserName"), o);
        }
    }

    /**
     * 根据用户昵称设置备注名称
     *
     * @param nickName
     * @param remarkName
     * @date 2017年5月27日 上午12:21:40
     */
    public static void remarkNameByNickName(String nickName, String remarkName) {
        String url = String.format(URLEnum.WEB_WX_REMARKNAME.getUrl(), core.getLoginInfo().get("url"),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
        Map<String, Object> msgMap = new HashMap<>();
        Map<String, Object> msgMapBaseRequest = new HashMap<>();
        msgMap.put("CmdId", 2);
        msgMap.put("RemarkName", remarkName);
        msgMap.put("UserName", core.getUserInfoMap().get(nickName).get("UserName"));
        msgMapBaseRequest.put("Uin", core.getLoginInfo().get(StorageLoginInfoEnum.wxuin.getKey()));
        msgMapBaseRequest.put("Sid", core.getLoginInfo().get(StorageLoginInfoEnum.wxsid.getKey()));
        msgMapBaseRequest.put("Skey", core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey()));
        msgMapBaseRequest.put("DeviceID", core.getLoginInfo().get(StorageLoginInfoEnum.deviceid.getKey()));
        msgMap.put("BaseRequest", msgMapBaseRequest);
        try {
            String paramStr = JSON.toJSONString(msgMap);
            core.getMyHttpClient().doPost(url, paramStr);
            logger.info("修改备注:{}", remarkName);
        } catch (Exception e) {
            logger.error("remarkNameByUserName", e);
        }
    }

    /**
     * 发送图片消息，内部调用
     *
     * @param userId
     * @param mediaId
     * @return
     */
    private static boolean sendImgMsg(String userId, String mediaId) {
        String url = String.format("%s/webwxsendmsgimg?fun=async&f=json&pass_ticket=%s", core.getLoginInfo().get("url"),
                core.getLoginInfo().get("pass_ticket"));
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("Type", 3);
        msgMap.put("MediaId", mediaId);
        msgMap.put("FromUserName", core.getUserSelf().getString("UserName"));
        msgMap.put("ToUserName", userId);
        String clientMsgId = String.valueOf(System.currentTimeMillis())
                + String.valueOf(new Random().nextLong()).substring(1, 5);
        msgMap.put("LocalID", clientMsgId);
        msgMap.put("ClientMsgId", clientMsgId);
        Map<String, Object> paramMap = core.getBaseParamMap();
        paramMap.put("BaseRequest", core.getBaseParamMap().get("BaseRequest"));
        paramMap.put("Msg", msgMap);
        String paramStr = JSON.toJSONString(paramMap);
        HttpEntity entity = core.getMyHttpClient().doPost(url, paramStr);
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity, Consts.UTF_8);
                return JSON.parseObject(result).getJSONObject("BaseResponse").getInteger("Ret") == 0;
            } catch (Exception e) {
                logger.error("发送图片消息错误", e);
            }
        }
        return false;
    }

    /**
     * 上传多媒体文件到微信服务器，目前应该支持3种类型:
     * 1. pic 直接显示，包含图片，表情 2.video 3.doc 显示为文件，包含PDF等
     *
     * @param filePath
     * @return
     */
    private static JSONObject uploadMediaFile(String filePath) {
        File f = new File(filePath);
        if (!f.exists() && f.isFile()) {
            logger.warn("file is not exist");
            return null;
        }
        String url = String.format(URLEnum.WEB_WX_UPLOAD_MEDIA.getUrl(), core.getLoginInfo().get("fileUrl"));
        String mimeType = new MimetypesFileTypeMap().getContentType(f);
        String mediaType = "";
        if (StringUtils.isEmpty(mimeType)) {
            mimeType = "text/plain";
        } else {
            mediaType = "image".equals(mimeType.split("/")[0]) ? "pic" : "doc";
        }
        String lastModifieDate = new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(new Date());
        long fileSize = f.length();
        String passTicket = (String) core.getLoginInfo().get("pass_ticket");
        String clientMediaId = String.valueOf(System.currentTimeMillis())
                + String.valueOf(new Random().nextLong()).substring(0, 4);
        String webwxDataTicket = MyHttpClient.getCookie("webwx_data_ticket");
        if (StringUtils.isEmpty(webwxDataTicket)) {
            logger.error("get cookie [webwx_data_ticket] error");
            return null;
        }
        Map<String, Object> paramMap = core.getBaseParamMap();
        paramMap.put("ClientMediaId", clientMediaId);
        paramMap.put("TotalLen", fileSize);
        paramMap.put("StartPos", 0);
        paramMap.put("DataLen", fileSize);
        paramMap.put("MediaType", 4);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addTextBody("id", "WU_FILE_0", ContentType.TEXT_PLAIN);
        builder.addTextBody("name", filePath, ContentType.TEXT_PLAIN);
        builder.addTextBody("type", mimeType, ContentType.TEXT_PLAIN);
        builder.addTextBody("lastModifieDate", lastModifieDate, ContentType.TEXT_PLAIN);
        builder.addTextBody("size", String.valueOf(fileSize), ContentType.TEXT_PLAIN);
        builder.addTextBody("mediatype", mediaType, ContentType.TEXT_PLAIN);
        builder.addTextBody("uploadmediarequest", JSON.toJSONString(paramMap), ContentType.TEXT_PLAIN);
        builder.addTextBody("webwx_data_ticket", webwxDataTicket, ContentType.TEXT_PLAIN);
        builder.addTextBody("pass_ticket", passTicket, ContentType.TEXT_PLAIN);
        builder.addBinaryBody("filename", f, ContentType.create(mimeType), filePath);
        HttpEntity reqEntity = builder.build();
        HttpEntity entity = core.getMyHttpClient().doPostFile(url, reqEntity);
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity, Consts.UTF_8);
                return JSON.parseObject(result);
            } catch (Exception e) {
                logger.error("上传多媒体文件错误", e);
            }

        }
        return null;
    }

    /**
     * 发送文件消息，内部调用
     *
     * @param userId
     * @param data
     * @return
     */
    private static boolean sendFileMsg(String userId, Map<String, String> data) {
        String url = String.format("%s/webwxsendappmsg?fun=async&f=json&pass_ticket=%s", core.getLoginInfo().get("url"),
                core.getLoginInfo().get("pass_ticket"));
        String clientMsgId = String.valueOf(System.currentTimeMillis())
                + String.valueOf(new Random().nextLong()).substring(1, 5);
        String content = "<appmsg appid='wxeb7ec651dd0aefa9' sdkver=''><title>" + data.get("title")
                + "</title><des></des><action></action><type>6</type><content></content><url></url><lowurl></lowurl>"
                + "<appattach><totallen>" + data.get("totallen") + "</totallen><attachid>" + data.get("attachid")
                + "</attachid><fileext>" + data.get("fileext") + "</fileext></appattach><extinfo></extinfo></appmsg>";
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("Type", data.get("type"));
        msgMap.put("Content", content);
        msgMap.put("FromUserName", core.getUserSelf().getString("UserName"));
        msgMap.put("ToUserName", userId);
        msgMap.put("LocalID", clientMsgId);
        msgMap.put("ClientMsgId", clientMsgId);
        Map<String, Object> paramMap = core.getBaseParamMap();
        paramMap.put("Msg", msgMap);
        paramMap.put("Scene", 0);
        String paramStr = JSON.toJSONString(paramMap);
        HttpEntity entity = core.getMyHttpClient().doPost(url, paramStr);
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity, Consts.UTF_8);
                return JSON.parseObject(result).getJSONObject("BaseResponse").getInteger("Ret") == 0;
            } catch (Exception e) {
                logger.error("错误: ", e);
            }
        }
        return false;
    }
}
