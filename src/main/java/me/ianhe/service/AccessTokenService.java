package me.ianhe.service;

import com.google.common.collect.Maps;
import me.ianhe.model.AccessToken;
import me.ianhe.model.WXAccessToken;
import me.ianhe.utils.DateTimeUtil;
import me.ianhe.utils.Global;
import me.ianhe.utils.WechatUtil;
import org.apache.commons.lang3.StringUtils;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class AccessTokenService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // access token 剩余时间预留安全值，半小时
    private static final long SAFE_TOKEN_RESERVE_TIME = 1000L * 30 * 60;
    public static final String FILE_NAME = "access_token.yml";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String GEN_TIME = "genTime";// 更新时间
    public static final String EXPIRES_TIME = "expiresTime";// 过期时间
    private Map<String, Object> accessTokenMap;

    public void run() {
        try {
            logger.info("check accessToken...");
            File file = new File(Global.getClassPath(), FILE_NAME);
            accessTokenMap = Yaml.loadType(file, HashMap.class);
            accessTokenMap = Maps.newHashMap();
            checkAndUpdate();
        } catch (Exception e) {
            logger.error("update access token error", e);
        }
    }

    /**
     * 定期检测access_token，若不存在或过期时间不足安全时间，则去远程更新
     */
    private synchronized void checkAndUpdate() {
        AccessToken accessToken = getAccessToken();
        if (accessToken == null || accessToken.getLeftValidTimeMillis() < SAFE_TOKEN_RESERVE_TIME) {
            WXAccessToken wxToken = WechatUtil.getAccessToken(Global.getAppId(), Global.getAppSecret());
            accessToken = transAccessToken(wxToken);
            updateTokenToFile(accessToken);
        }
    }

    /**
     * 从map中获取AccessToken值
     *
     * @return
     */
    public AccessToken getAccessToken() {
        String tokenStr = (String) accessTokenMap.get(ACCESS_TOKEN);
        if (StringUtils.isBlank(tokenStr)) {
            return null;
        }
        AccessToken accessToken = new AccessToken();
        accessToken.setToken(tokenStr);
        accessToken.setGenTime((Date) accessTokenMap.get(GEN_TIME));
        accessToken.setExpiresTime((Date) accessTokenMap.get(EXPIRES_TIME));
        return accessToken;
    }

    /**
     * 微信accessToken转化为存储Token
     *
     * @param wxAccessToken
     * @return AccessToken
     */
    private AccessToken transAccessToken(WXAccessToken wxAccessToken) {
        AccessToken accessToken = new AccessToken();
        accessToken.setToken(wxAccessToken.getAccess_token());
        Calendar now = Calendar.getInstance();
        accessToken.setGenTime(now.getTime());
        now.add(Calendar.MILLISECOND, wxAccessToken.getExpires_in() * 1000);
        Date validUntil = new Date(now.getTime().getTime());
        accessToken.setExpiresTime(validUntil);
        return accessToken;
    }

    private int updateTokenToFile(AccessToken accessToken) {
        accessTokenMap.put("updateTime", DateTimeUtil.formatSecond(accessToken.getGenTime()));
        accessTokenMap.put(ACCESS_TOKEN, accessToken.getToken());
        accessTokenMap.put(GEN_TIME, accessToken.getGenTime());
        accessTokenMap.put(EXPIRES_TIME, accessToken.getExpiresTime());
        File file = new File(Global.getClassPath(), FILE_NAME);
        try {
            Yaml.dump(accessTokenMap, file);
            return 1;
        } catch (IOException e) {
            logger.error("写入AccessToken文件异常：", e);
            return 0;
        }
    }

}
