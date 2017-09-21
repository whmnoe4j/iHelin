package me.ianhe.config;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.extend.NotifyListener;
import org.springframework.stereotype.Repository;

/**
 * @author iHelin
 * @since 2017/9/21 10:28
 */
@Repository
public class QQNotifyListener implements NotifyListener {

    private SmartQQClient smartQQClient;

    @Override
    public void hander(PollMsg pollMsg) {
        switch (pollMsg.getMsgType()) {
            case message:
                smartQQClient.sendMsg(new SendMsg(pollMsg, "I Have Got Your Msg: friend"));
                break;
        }
    }

    public SmartQQClient getSmartQQClient() {
        return smartQQClient;
    }

    public void setSmartQQClient(SmartQQClient smartQQClient) {
        this.smartQQClient = smartQQClient;
    }
}
