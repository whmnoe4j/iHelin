package me.ianhe.wechat.thread;

import me.ianhe.wechat.core.Core;
import me.ianhe.wechat.utils.WeChatTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 检查微信在线状态
 * 如何来感知微信状态
 * 微信会有心跳包，LoginServiceImpl.syncCheck()正常在线情况下返回的消息中retcode报文应该为"0"，心跳间隔一般在25秒，
 * 那么可以通过最后收到正常报文的时间来作为判断是否在线的依据。若报文间隔大于60秒，则认为已掉线。
 *
 * @author iHelin
 * @since 2017/12/11 14:29
 */
public class CheckLoginStatusThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Core core = Core.getInstance();

    @Override
    public void run() {
        while (core.isAlive()) {
            // 秒为单位
            long t1 = System.currentTimeMillis();
            // 超过180秒，判为离线
            if (t1 - core.getLastNormalRetcodeTime() > 3L * 60 * 1000) {
                WeChatTools.logout();
                logger.info("微信已离线");
            }
            // 休眠10秒
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
