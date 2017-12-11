package me.ianhe.wechat.utils;

import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.core.Core;
import me.ianhe.wechat.enums.MsgTypeEnum;
import me.ianhe.wechat.enums.URLEnum;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下载工具类
 *
 * @author iHelin
 * @since 2017/12/11 14:32
 */
public class DownloadTools {
    private static Core core = Core.getInstance();

    /**
     * 处理下载任务
     *
     * @param msg
     * @param type
     */
    public static byte[] getDownloadFn(BaseMsg msg, String type) {
        Map<String, String> headerMap = new HashMap<>();
        List<BasicNameValuePair> params = new ArrayList<>();
        String url = "";
        if (type.equals(MsgTypeEnum.PIC.getType())) {
            url = String.format(URLEnum.WEB_WX_GET_MSG_IMG.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (type.equals(MsgTypeEnum.VOICE.getType())) {
            url = String.format(URLEnum.WEB_WX_GET_VOICE.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (type.equals(MsgTypeEnum.VIEDO.getType())) {
            headerMap.put("Range", "bytes=0-");
            url = String.format(URLEnum.WEB_WX_GET_VIEDO.getUrl(), (String) core.getLoginInfo().get("url"));
        } else if (type.equals(MsgTypeEnum.MEDIA.getType())) {
            headerMap.put("Range", "bytes=0-");
            url = String.format(URLEnum.WEB_WX_GET_MEDIA.getUrl(), (String) core.getLoginInfo().get("fileUrl"));
            params.add(new BasicNameValuePair("sender", msg.getFromUserName()));
            params.add(new BasicNameValuePair("mediaid", msg.getMediaId()));
            params.add(new BasicNameValuePair("filename", msg.getFileName()));
        }
        params.add(new BasicNameValuePair("msgid", msg.getNewMsgId()));
        params.add(new BasicNameValuePair("skey", (String) core.getLoginInfo().get("skey")));
        HttpEntity entity = core.getMyHttpClient().doGet(url, params, true, headerMap);
//        CommonTools.saveFile(new File(path), entity);
        try {
            byte[] bytes = EntityUtils.toByteArray(entity);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
