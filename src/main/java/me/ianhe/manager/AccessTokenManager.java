package me.ianhe.manager;

import com.google.common.collect.Maps;
import me.ianhe.config.CommonConfig;
import me.ianhe.model.AccessToken;
import me.ianhe.model.WXAccessToken;
import me.ianhe.utils.DateTimeUtil;
import me.ianhe.utils.WechatUtil;
import org.apache.commons.lang3.StringUtils;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class AccessTokenManager {

    // access token 剩余时间预留安全值，半小时
    private static final long SAFE_TOKEN_RESERVE_TIME = 1000L * 30 * 60;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenManager.class);
    public static final String fileName = "access_token.yml";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String GEN_TIME = "genTime";// 更新时间
    public static final String EXPIRES_TIME = "expiresTime";// 过期时间
    private static Map<String, Object> accessTokenMap;

    // 获取token
    public AccessToken getAccessToken() {
        String token = (String) accessTokenMap.get(ACCESS_TOKEN);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        AccessToken accessToken = new AccessToken();
        accessToken.setToken(token);
        accessToken.setGenTime((Date) accessTokenMap.get(GEN_TIME));
        accessToken.setExpiresTime((Date) accessTokenMap.get(EXPIRES_TIME));
        return accessToken;
    }

    private void loadFile() {
        File file = new File(CommonConfig.getWebInfDir(), fileName);
        try {
            accessTokenMap = Yaml.loadType(file, HashMap.class);
        } catch (Exception e) {
            accessTokenMap = Maps.newHashMap();
            LOGGER.warn("loading throws exception, file path: " + fileName, e);
        }
    }

    // 设置定时器，定期检查
    @PostConstruct
    public void init() {
        loadFile();
        Timer t = new Timer("access-token-updater");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    LOGGER.info("check accessToken...");
                    checkAndUpdate();
                } catch (Exception e) {
                    LOGGER.error("update access token error", e);
                }
            }
        }, 0, 1000L * 60 * 30);
    }

    // 强制更新token
    public boolean forceUpdateAccessToken() {
        WXAccessToken wxToken = WechatUtil.getAccessToken(CommonConfig.getAppID(), CommonConfig.getAppSecret());
        if (wxToken == null || StringUtils.isBlank(wxToken.getAccess_token())) {
            return false;
        }
        AccessToken accessToken = transAccessToken(wxToken);
        updateTokenToFile(accessToken);
        LOGGER.info("强制更新access_token成功。");
        return true;
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

    /**
     * 定期检测access_token，若过期时间不足安全时间，则更新
     */
    private synchronized void checkAndUpdate() {
        AccessToken accessToken = getAccessToken();
        if (accessToken == null) {
            WXAccessToken wxToken = WechatUtil.getAccessToken(CommonConfig.getAppID(), CommonConfig.getAppSecret());
            accessToken = transAccessToken(wxToken);
            updateTokenToFile(accessToken);
        } else {
            if (accessToken.getLeftValidTimeMillis() < SAFE_TOKEN_RESERVE_TIME) {
                WXAccessToken wxToken = WechatUtil.getAccessToken(CommonConfig.getAppID(), CommonConfig.getAppSecret());
                accessToken = transAccessToken(wxToken);
                updateTokenToFile(accessToken);
            }
        }
    }

    private int updateTokenToFile(AccessToken accessToken) {
        accessTokenMap.put("updateTime", DateTimeUtil.formatSecond(accessToken.getGenTime()));
        accessTokenMap.put(ACCESS_TOKEN, accessToken.getToken());
        accessTokenMap.put(GEN_TIME, accessToken.getGenTime());
        accessTokenMap.put(EXPIRES_TIME, accessToken.getExpiresTime());
        File file = new File(CommonConfig.getWebInfDir(), fileName);
        try {
            Yaml.dump(accessTokenMap, file);
            return 1;
        } catch (IOException e) {
            LOGGER.error("写入文件异常：", e);
            return 0;
        }
    }

}
