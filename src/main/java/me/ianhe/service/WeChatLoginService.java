package me.ianhe.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.core.Core;
import me.ianhe.wechat.core.MsgCenter;
import me.ianhe.wechat.enums.ResultEnum;
import me.ianhe.wechat.enums.RetCodeEnum;
import me.ianhe.wechat.enums.StorageLoginInfoEnum;
import me.ianhe.wechat.enums.URLEnum;
import me.ianhe.wechat.enums.parameters.BaseParaEnum;
import me.ianhe.wechat.enums.parameters.LoginParaEnum;
import me.ianhe.wechat.enums.parameters.StatusNotifyParaEnum;
import me.ianhe.wechat.enums.parameters.UUIDParaEnum;
import me.ianhe.wechat.thread.CheckLoginStatusThread;
import me.ianhe.wechat.utils.CommonTools;
import me.ianhe.wechat.utils.WeChatTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.OutputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * 登录服务
 *
 * @author iHelin
 * @since 2017/12/11 14:08
 */
@Service
public class WeChatLoginService {

    private static final ArrayList<String> API_SPECIAL_USER = new ArrayList<>(Arrays.asList("filehelper", "weibo",
            "qqmail", "fmessage", "tmessage", "qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote",
            "qqfriend", "readerapp", "blogapp", "facebookapp", "masssendapp", "meishiapp", "feedsapp", "voip",
            "blogappweixin", "brandsessionholder", "weixin", "weixinreminder", "officialaccounts", "wxitil",
            "notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "userexperience_alarm"));
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Core core = Core.getInstance();

    public void login(OutputStream qrPath) {
        // 已登录
        if (core.isAlive()) {
            logger.info("用户已登录");
            return;
        }
        while (true) {
            logger.info("1 获取UUID");
            String uuid = getUuid();
            while (StringUtils.isEmpty(uuid)) {
                logger.warn("1.1 获取微信UUID失败，三秒后重新获取");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    uuid = getUuid();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            logger.info("2 获取登录二维码图片");
            boolean success = getQR(qrPath);
            while (!success) {
                logger.warn("2.1 获取登录二维码图片失败，三秒后重新获取");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    success = getQR(qrPath);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            logger.info("3 请扫描二维码图片，并在手机上确认");
            if (!core.isAlive()) {
                login();
                logger.info(("登录成功"));
                break;
            }
            logger.warn("3.1 登录超时，请重新扫描二维码图片");
        }

        logger.info("4 登录成功，微信初始化");
        if (!weChatInit()) {
            logger.error("4.1 微信初始化异常");
            return;
        }

        logger.info("5 开启微信状态通知");
        wxStatusNotify();

        logger.info(String.format("6 欢迎回来， %s", core.getNickName()));

        logger.info("7 开始接收消息");
        startReceiving();

        logger.info("8 获取联系人信息");
        webWxGetContact();

        logger.info("9 获取群好友及群好友列表");
        webWxBatchGetContact();

        logger.info("10 缓存本次登录好友相关消息");
        WeChatTools.setUserInfo();

        logger.info("11 开启微信状态检测线程");
        //开始检测在线状态
        Runnable r = new CheckLoginStatusThread();
        new Thread(r).start();
    }

    /**
     * 登录
     *
     * @author iHelin
     * @since 2017/12/11 14:08
     */
    public void login() {
        // 组装参数和URL
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(LoginParaEnum.LOGIN_ICON.getParam(), LoginParaEnum.LOGIN_ICON.value()));
        params.add(new BasicNameValuePair(LoginParaEnum.UUID.getParam(), core.getUuid()));
        params.add(new BasicNameValuePair(LoginParaEnum.TIP.getParam(), LoginParaEnum.TIP.value()));
        while (!core.isAlive()) {
            long millis = System.currentTimeMillis();
            params.add(new BasicNameValuePair(LoginParaEnum.R.getParam(), String.valueOf(millis / 1579L)));
            params.add(new BasicNameValuePair(LoginParaEnum.TIME_STAMP.getParam(), String.valueOf(millis)));
            HttpEntity entity = core.getMyHttpClient().doGet(URLEnum.LOGIN_URL.getUrl(), params, true, null);
            try {
                String result = EntityUtils.toString(entity);
                String status = checkLogin(result);

                if (ResultEnum.SUCCESS.getCode().equals(status)) {
                    processLoginInfo(result);
                    core.setAlive(true);
                    break;
                } else if (ResultEnum.WAIT_CONFIRM.getCode().equals(status)) {
                    logger.info("请点击微信登录按钮，进行登录");
                } else if (ResultEnum.WAIT_SCAN.getCode().equals(status)) {
                    logger.info("请扫码");
                } else {
                    logger.error("未知状态码(可能是二维码过期)：" + status);
                    break;
                }
            } catch (Exception e) {
                logger.error("微信登录异常！", e);
            }
        }
    }

    /**
     * 获取UUID
     *
     * @author iHelin
     * @since 2017/12/11 14:08
     */
    public String getUuid() {
        // 组装参数和URL
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(UUIDParaEnum.APP_ID.para(), UUIDParaEnum.APP_ID.value()));
        params.add(new BasicNameValuePair(UUIDParaEnum.FUN.para(), UUIDParaEnum.FUN.value()));
        params.add(new BasicNameValuePair(UUIDParaEnum.LANG.para(), UUIDParaEnum.LANG.value()));
        params.add(new BasicNameValuePair(UUIDParaEnum.TIME_STAMP.para(), String.valueOf(System.currentTimeMillis())));

        HttpEntity entity = core.getMyHttpClient().doGet(URLEnum.UUID_URL.getUrl(), params, true, null);
        String uuid = "";
        try {
            String result = EntityUtils.toString(entity);
            String regEx = "window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";";
            Matcher matcher = CommonTools.getMatcher(regEx, result);
            if (matcher.find()) {
                if ((ResultEnum.SUCCESS.getCode().equals(matcher.group(1)))) {
                    uuid = matcher.group(2);
                    core.setUuid(uuid);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return uuid;
    }

    /**
     * 获取二维码图片
     *
     * @author iHelin
     * @since 2017/12/11 14:08
     */
    public boolean getQR(OutputStream qrPath) {
        String qrUrl = URLEnum.QRCODE_URL.getUrl() + core.getUuid();
        HttpEntity entity = core.getMyHttpClient().doGet(qrUrl, null, true, null);
        CommonTools.translateToOutputStream(qrPath, entity);
        return true;
    }

    /**
     * web初始化
     *
     * @author iHelin
     * @since 2017/12/11 14:09
     */
    public boolean weChatInit() {
        //设置最近一次正常返回时间
        core.setLastNormalRetcodeTime(System.currentTimeMillis());
        // 组装请求URL和参数
        String url = String.format(URLEnum.INIT_URL.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()),
                String.valueOf(System.currentTimeMillis() / 3158L),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));

        Map<String, Object> paramMap = core.getBaseParamMap();

        // 请求初始化接口
        HttpEntity entity = core.getMyHttpClient().doPost(url, JSON.toJSONString(paramMap));
        try {
            String result = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject obj = JSON.parseObject(result);
            JSONObject user = obj.getJSONObject(StorageLoginInfoEnum.USER.getKey());
            JSONObject syncKey = obj.getJSONObject(StorageLoginInfoEnum.SyncKey.getKey());

            core.getLoginInfo().put(StorageLoginInfoEnum.InviteStartCount.getKey(),
                    obj.getInteger(StorageLoginInfoEnum.InviteStartCount.getKey()));
            core.getLoginInfo().put(StorageLoginInfoEnum.SyncKey.getKey(), syncKey);

            JSONArray syncArray = syncKey.getJSONArray("List");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < syncArray.size(); i++) {
                sb.append(syncArray.getJSONObject(i).getString("Key") + "_"
                        + syncArray.getJSONObject(i).getString("Val") + "|");
            }
            String synckey = sb.toString();
            core.getLoginInfo().put(StorageLoginInfoEnum.synckey.getKey(), synckey.substring(0, synckey.length() - 1));
            core.setUserSelf(user);
            core.setUserName(user.getString("UserName"));
            core.setNickName(user.getString("NickName"));
            String chatSet = obj.getString("ChatSet");
            String[] chatSetArray = chatSet.split(",");
            for (String aChatSetArray : chatSetArray) {
                if (aChatSetArray.contains("@@")) {
                    // 更新GroupIdList
                    core.addGroupId(aChatSetArray);
                }
            }
        } catch (Exception e) {
            logger.error("初始化异常");
            return false;
        }
        return true;
    }

    /**
     * 微信状态通知
     *
     * @author iHelin
     * @since 2017/12/11 14:09
     */
    public void wxStatusNotify() {
        // 组装请求URL和参数
        String url = String.format(URLEnum.STATUS_NOTIFY_URL.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
        Map<String, Object> paramMap = core.getBaseParamMap();
        paramMap.put(StatusNotifyParaEnum.CODE.para(), StatusNotifyParaEnum.CODE.value());
        paramMap.put(StatusNotifyParaEnum.FROM_USERNAME.para(), core.getUserName());
        paramMap.put(StatusNotifyParaEnum.TO_USERNAME.para(), core.getUserName());
        paramMap.put(StatusNotifyParaEnum.CLIENT_MSG_ID.para(), System.currentTimeMillis());
        String paramStr = JSON.toJSONString(paramMap);
        try {
            HttpEntity entity = core.getMyHttpClient().doPost(url, paramStr);
            EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            logger.error("微信状态通知接口失败！", e);
        }

    }

    /**
     * 接收消息
     *
     * @author iHelin
     * @since 2017/12/11 14:09
     */
    public void startReceiving() {
        new Thread(new Runnable() {
            int retryCount = 0;

            @Override
            public void run() {
                while (core.isAlive()) {
                    try {
                        Map<String, String> resultMap = syncCheck();
                        logger.info(JSONObject.toJSONString(resultMap));
                        String retcode = resultMap.get("retcode");
                        String selector = resultMap.get("selector");
                        if (RetCodeEnum.UNKOWN.getCode().equals(retcode)) {
                            logger.info(RetCodeEnum.UNKOWN.getType());
                        } else if (RetCodeEnum.LOGIN_OUT.getCode().equals(retcode)) {
                            // 退出
                            logger.info(RetCodeEnum.LOGIN_OUT.getType());
                            break;
                        } else if (RetCodeEnum.LOGIN_OTHERWHERE.getCode().equals(retcode)) {
                            // 其它地方登录
                            logger.info(RetCodeEnum.LOGIN_OTHERWHERE.getType());
                            break;
                        } else if (RetCodeEnum.MOBILE_LOGIN_OUT.getCode().equals(retcode)) {
                            // 移动端退出
                            logger.info(RetCodeEnum.MOBILE_LOGIN_OUT.getType());
                            break;
                        } else if (RetCodeEnum.NORMAL.getCode().equals(retcode)) {
                            // 最后收到正常报文时间
                            core.setLastNormalRetcodeTime(System.currentTimeMillis());
                            JSONObject msgObj = webWxSync();
                            if ("2".equals(selector)) {
                                if (msgObj != null) {
                                    try {
                                        JSONArray msgList = msgObj.getJSONArray("AddMsgList");
                                        msgList = MsgCenter.produceMsg(msgList);
                                        for (int j = 0; j < msgList.size(); j++) {
                                            BaseMsg baseMsg = JSON.toJavaObject(msgList.getJSONObject(j),
                                                    BaseMsg.class);
                                            core.getMsgList().add(baseMsg);
                                        }
                                    } catch (Exception e) {
                                        logger.info(e.getMessage());
                                    }
                                }
                            } else if ("7".equals(selector)) {
                                webWxSync();
                            } else if ("4".equals(selector)) {
                            } else if ("3".equals(selector)) {
                            } else if ("6".equals(selector)) {
                                if (msgObj != null) {
                                    try {
                                        JSONArray msgList = msgObj.getJSONArray("AddMsgList");
                                        // 存在删除或者新增的好友信息
                                        JSONArray modContactList = msgObj.getJSONArray("ModContactList");
                                        msgList = MsgCenter.produceMsg(msgList);
                                        for (int j = 0; j < msgList.size(); j++) {
                                            JSONObject userInfo = modContactList.getJSONObject(j);
                                            // 存在主动加好友之后的同步联系人到本地
                                            core.getContactList().add(userInfo);
                                        }
                                    } catch (Exception e) {
                                        logger.info(e.getMessage());
                                    }
                                }
                            }
                        } else {
                            webWxSync();
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                        retryCount += 1;
                        if (core.getReceivingRetryCount() < retryCount) {
                            core.setAlive(false);
                        } else {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                logger.info(e.getMessage());
                            }
                        }
                    }

                }
            }
        }).start();

    }

    /**
     * 获取微信联系人
     *
     * @author iHelin
     * @since 2017/12/11 14:09
     */
    public void webWxGetContact() {
        String url = String.format(URLEnum.WEB_WX_GET_CONTACT.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()));
        Map<String, Object> paramMap = core.getBaseParamMap();
        HttpEntity entity = core.getMyHttpClient().doPost(url, JSON.toJSONString(paramMap));

        try {
            String result = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject fullFriendsJsonList = JSON.parseObject(result);
            // 查看seq是否为0，0表示好友列表已全部获取完毕，若大于0，则表示好友列表未获取完毕，当前的字节数（断点续传）
            long seq = 0;
            long currentTime = 0L;
            List<BasicNameValuePair> params = new ArrayList<>();
            if (fullFriendsJsonList.get("Seq") != null) {
                seq = fullFriendsJsonList.getLong("Seq");
                currentTime = System.currentTimeMillis();
            }
            JSONArray member = fullFriendsJsonList.getJSONArray(StorageLoginInfoEnum.MemberList.getKey());
            // 循环获取seq直到为0，即获取全部好友列表 ==0：好友获取完毕 >0：好友未获取完毕，此时seq为已获取的字节数
            while (seq > 0) {
                // 设置seq传参
                params.add(new BasicNameValuePair("r", String.valueOf(currentTime)));
                params.add(new BasicNameValuePair("seq", String.valueOf(seq)));
                entity = core.getMyHttpClient().doGet(url, params, false, null);

                params.remove(new BasicNameValuePair("r", String.valueOf(currentTime)));
                params.remove(new BasicNameValuePair("seq", String.valueOf(seq)));

                result = EntityUtils.toString(entity, Consts.UTF_8);
                fullFriendsJsonList = JSON.parseObject(result);

                if (fullFriendsJsonList.get("Seq") != null) {
                    seq = fullFriendsJsonList.getLong("Seq");
                    currentTime = System.currentTimeMillis();
                }

                // 累加好友列表
                member.addAll(fullFriendsJsonList.getJSONArray(StorageLoginInfoEnum.MemberList.getKey()));
            }
            core.setMemberCount(member.size());
            for (Object aMember : member) {
                JSONObject o = (JSONObject) aMember;
                if ((o.getInteger("VerifyFlag") & 8) != 0) {
                    // 公众号/服务号
                    core.getPublicUsersList().add(o);
                } else if (API_SPECIAL_USER.contains(o.getString("UserName"))) {
                    // 特殊账号
                    core.getSpecialUsersList().add(o);
                } else if (o.getString("UserName").contains("@@")) {
                    // 群聊
                    if (!core.getGroupIdList().contains(o.getString("UserName"))) {
                        core.getGroupNickNameList().add(o.getString("NickName"));
                        core.getGroupIdList().add(o.getString("UserName"));
                        core.getGroupList().add(o);
                    }
                } else if (o.getString("UserName").equals(core.getUserSelf().getString("UserName"))) {
                    // 自己
                    core.getContactList().remove(o);
                } else {
                    // 普通联系人
                    core.getContactList().add(o);
                }
            }
            return;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 批量获取联系人信息
     *
     * @author iHelin
     * @since 2017/12/11 14:09
     */
    public void webWxBatchGetContact() {
        String url = String.format(URLEnum.WEB_WX_BATCH_GET_CONTACT.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()), System.currentTimeMillis(),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
        Map<String, Object> paramMap = core.getBaseParamMap();
        paramMap.put("Count", core.getGroupIdList().size());
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < core.getGroupIdList().size(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("UserName", core.getGroupIdList().get(i));
            map.put("EncryChatRoomId", "");
            list.add(map);
        }
        paramMap.put("List", list);
        HttpEntity entity = core.getMyHttpClient().doPost(url, JSON.toJSONString(paramMap));
        try {
            String text = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject obj = JSON.parseObject(text);
            JSONArray contactList = obj.getJSONArray("ContactList");
            for (int i = 0; i < contactList.size(); i++) {
                // 群好友
                if (contactList.getJSONObject(i).getString("UserName").contains("@@")) {
                    // 群
                    // 更新群昵称列表
                    core.getGroupNickNameList().add(contactList.getJSONObject(i).getString("NickName"));
                    // 更新群信息（所有）列表
                    core.getGroupList().add(contactList.getJSONObject(i));
                    // 更新群成员Map
                    core.getGroupMemeberMap().put(contactList.getJSONObject(i).getString("UserName"),
                            contactList.getJSONObject(i).getJSONArray("MemberList"));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 检查登录状态
     *
     * @param result
     * @return
     */
    private String checkLogin(String result) {
        String regEx = "window.code=(\\d+)";
        Matcher matcher = CommonTools.getMatcher(regEx, result);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 处理登录信息
     *
     * @param loginContent
     */
    private void processLoginInfo(String loginContent) {
        String regEx = "window.redirect_uri=\"(\\S+)\";";
        Matcher matcher = CommonTools.getMatcher(regEx, loginContent);
        if (matcher.find()) {
            String originalUrl = matcher.group(1);
            String url = originalUrl.substring(0, originalUrl.lastIndexOf('/'));
            core.getLoginInfo().put("url", url);
            Map<String, List<String>> possibleUrlMap = getPossibleUrlMap();
            Iterator<Entry<String, List<String>>> iterator = possibleUrlMap.entrySet().iterator();
            Entry<String, List<String>> entry;
            String fileUrl;
            String syncUrl;
            while (iterator.hasNext()) {
                entry = iterator.next();
                String indexUrl = entry.getKey();
                fileUrl = "https://" + entry.getValue().get(0) + "/cgi-bin/mmwebwx-bin";
                syncUrl = "https://" + entry.getValue().get(1) + "/cgi-bin/mmwebwx-bin";
                if (core.getLoginInfo().get("url").toString().contains(indexUrl)) {
                    core.setIndexUrl(indexUrl);
                    core.getLoginInfo().put("fileUrl", fileUrl);
                    core.getLoginInfo().put("syncUrl", syncUrl);
                    break;
                }
            }
            if (core.getLoginInfo().get("fileUrl") == null && core.getLoginInfo().get("syncUrl") == null) {
                core.getLoginInfo().put("fileUrl", url);
                core.getLoginInfo().put("syncUrl", url);
            }
            // 生成15位随机数
            core.getLoginInfo().put("deviceid", "e" + String.valueOf(new Random().nextLong()).substring(1, 16));
            core.getLoginInfo().put("BaseRequest", new ArrayList<String>());
            String text;

            try {
                HttpEntity entity = core.getMyHttpClient().doGet(originalUrl, null, false, null);
                text = EntityUtils.toString(entity);
            } catch (Exception e) {
                logger.info(e.getMessage());
                return;
            }
            //如果登录被禁止时，则登录返回的message内容不为空，下面代码则判断登录内容是否为空，不为空则退出程序
            String msg = getLoginMessage(text);
            if (!"".equals(msg)) {
                logger.error("登录被禁止" + msg);
                return;
            }
            Document doc = CommonTools.xmlParser(text);
            if (doc != null) {
                core.getLoginInfo().put(StorageLoginInfoEnum.skey.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.skey.getKey()).item(0).getFirstChild()
                                .getNodeValue());
                core.getLoginInfo().put(StorageLoginInfoEnum.wxsid.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.wxsid.getKey()).item(0).getFirstChild()
                                .getNodeValue());
                core.getLoginInfo().put(StorageLoginInfoEnum.wxuin.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.wxuin.getKey()).item(0).getFirstChild()
                                .getNodeValue());
                core.getLoginInfo().put(StorageLoginInfoEnum.pass_ticket.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.pass_ticket.getKey()).item(0).getFirstChild()
                                .getNodeValue());
            }

        }
    }

    private Map<String, List<String>> getPossibleUrlMap() {
        Map<String, List<String>> possibleUrlMap = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        list1.add("file.wx.qq.com");
        list1.add("webpush.wx.qq.com");
        possibleUrlMap.put("wx.qq.com", list1);

        List<String> list2 = new ArrayList<>();
        list2.add("file.wx2.qq.com");
        list2.add("webpush.wx2.qq.com");
        possibleUrlMap.put("wx2.qq.com", list2);

        List<String> list3 = new ArrayList<>();
        list3.add("file.wx8.qq.com");
        list3.add("webpush.wx8.qq.com");
        possibleUrlMap.put("wx8.qq.com", list3);

        List<String> list4 = new ArrayList<>();
        list4.add("file.web2.wechat.com");
        list4.add("webpush.web2.wechat.com");
        possibleUrlMap.put("web2.wechat.com", list4);

        List<String> list5 = new ArrayList<>();
        list5.add("file.web.wechat.com");
        list5.add("webpush.web.wechat.com");
        possibleUrlMap.put("wechat.com", list5);
        return possibleUrlMap;
    }

    /**
     * 同步消息 sync the messages
     *
     * @return
     * @author https://github.com/yaphone
     * @date 2017年5月12日 上午12:24:55
     */
    private JSONObject webWxSync() {
        JSONObject result = null;
        String url = String.format(URLEnum.WEB_WX_SYNC_URL.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()),
                core.getLoginInfo().get(StorageLoginInfoEnum.wxsid.getKey()),
                core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey()),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
        Map<String, Object> paramMap = core.getBaseParamMap();
        paramMap.put(StorageLoginInfoEnum.SyncKey.getKey(),
                core.getLoginInfo().get(StorageLoginInfoEnum.SyncKey.getKey()));
        paramMap.put("rr", -System.currentTimeMillis() / 1000);
        String paramStr = JSON.toJSONString(paramMap);
        try {
            HttpEntity entity = core.getMyHttpClient().doPost(url, paramStr);
            String text = EntityUtils.toString(entity, Consts.UTF_8);
            JSONObject obj = JSON.parseObject(text);
            if (obj.getJSONObject("BaseResponse").getInteger("Ret") != 0) {
                result = null;
            } else {
                result = obj;
                core.getLoginInfo().put(StorageLoginInfoEnum.SyncKey.getKey(), obj.getJSONObject("SyncCheckKey"));
                JSONArray syncArray = obj.getJSONObject(StorageLoginInfoEnum.SyncKey.getKey()).getJSONArray("List");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < syncArray.size(); i++) {
                    sb.append(syncArray.getJSONObject(i).getString("Key") + "_"
                            + syncArray.getJSONObject(i).getString("Val") + "|");
                }
                String synckey = sb.toString();
                // 1_656161336|2_656161626|3_656161313|11_656159955|13_656120033|201_1492273724|1000_1492265953|1001_1492250432|1004_1491805192
                core.getLoginInfo().put(StorageLoginInfoEnum.synckey.getKey(),
                        synckey.substring(0, synckey.length() - 1));
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return result;

    }

    /**
     * 检查是否有新消息 check whether there's a message
     *
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月16日 上午11:11:34
     */
    private Map<String, String> syncCheck() {
        Map<String, String> resultMap = new HashMap<>();
        // 组装请求URL和参数
        String url = core.getLoginInfo().get(StorageLoginInfoEnum.syncUrl.getKey()) + URLEnum.SYNC_CHECK_URL.getUrl();
        List<BasicNameValuePair> params = new ArrayList<>();
        for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
            params.add(new BasicNameValuePair(baseRequest.getParam().toLowerCase(),
                    core.getLoginInfo().get(baseRequest.getValue()).toString()));
        }
        params.add(new BasicNameValuePair("r", String.valueOf(System.currentTimeMillis())));
        params.add(new BasicNameValuePair("synckey", (String) core.getLoginInfo().get("synckey")));
        params.add(new BasicNameValuePair("_", String.valueOf(System.currentTimeMillis())));
        try {
            TimeUnit.MILLISECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            HttpEntity entity = core.getMyHttpClient().doGet(url, params, true, null);
            if (entity == null) {
                resultMap.put("retcode", "9999");
                resultMap.put("selector", "9999");
                return resultMap;
            }
            String text = EntityUtils.toString(entity);
            String regEx = "window.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"\\}";
            Matcher matcher = CommonTools.getMatcher(regEx, text);
            if (!matcher.find() || "2".equals(matcher.group(1))) {
                logger.info(String.format("Unexpected sync check result: %s", text));
            } else {
                resultMap.put("retcode", matcher.group(1));
                resultMap.put("selector", matcher.group(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 解析登录返回的消息，如果成功登录，则message为空
     *
     * @param result
     * @return
     */
    private String getLoginMessage(String result) {
        String[] strArr = result.split("<message>");
        String[] rs = strArr[1].split("</message>");
        if (rs != null && rs.length > 1) {
            return rs[0];
        }
        return "";
    }
}
