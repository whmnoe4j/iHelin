package me.ianhe.service;

import me.ianhe.model.AccessTokenTimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Timer;

@Service
public class AccessTokenService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private AccessTokenTimerTask accessTokenTimerTask;


    // 设置定时器，定期检查
    @PostConstruct
    public void init() {
        accessTokenTimerTask = new AccessTokenTimerTask();
        Timer t = new Timer("access-token-updater");
        t.scheduleAtFixedRate(accessTokenTimerTask, 0, 1000L * 60 * 30);
    }

    public void cancel() {
        accessTokenTimerTask.cancel();
        logger.debug("stoped!!!");
    }

}
