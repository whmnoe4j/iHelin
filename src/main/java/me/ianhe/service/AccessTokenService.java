package me.ianhe.service;

import com.google.common.collect.Maps;
import me.ianhe.model.AccessToken;
import me.ianhe.model.AccessTokenTimerTask;
import me.ianhe.model.WXAccessToken;
import me.ianhe.utils.DateTimeUtil;
import me.ianhe.utils.Global;
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
public class AccessTokenService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AccessTokenTimerTask accessTokenTimerTask;


    // 设置定时器，定期检查
    @PostConstruct
    public void init() {
        accessTokenTimerTask = new AccessTokenTimerTask();
        Timer t = new Timer("access-token-updater");
        t.scheduleAtFixedRate(accessTokenTimerTask, 0, 1000L * 5);
    }

    public void cancel(){
        accessTokenTimerTask.cancel();
        logger.debug("stoped!!!");
    }

    // 强制更新token
//    public boolean forceUpdateAccessToken() {
//        WXAccessToken wxToken = WechatUtil.getAccessToken(Global.getAppId(), Global.getAppSecret());
//        if (wxToken == null || StringUtils.isBlank(wxToken.getAccess_token())) {
//            return false;
//        }
//        AccessToken accessToken = transAccessToken(wxToken);
//        updateTokenToFile(accessToken);
//        LOGGER.info("强制更新access_token成功。");
//        return true;
//    }


}
