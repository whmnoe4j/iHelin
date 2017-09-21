package me.ianhe.config;

import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.WQQClient;
import com.thankjava.wqq.consts.MsgType;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.*;
import com.thankjava.wqq.extend.NotifyListener;
import me.ianhe.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/9/20 22:39
 */
public class TestSmartQQ {
    private static final Logger logger = LoggerFactory.getLogger(TestSmartQQ.class);

    // 初始化SmartQQClient
    // 需要指明一个NotifyListener 该接口的实例会在 SmartQQClient 拉取到信息时被执行调用
    static final SmartQQClient smartQQClient = new WQQClient(new NotifyListener() {

        @Override
        public void hander(PollMsg pollMsg) {
            // 这里让NotifyListener.hander由于拉取到信息而执行时,将执行的方法交由NotifyHander.hander去处理
            // 在NotifyHander里面对消息进行拓展处理
            notifyHander.hander(pollMsg);
        }

    });

    // 一个自定义用于处理得到消息的拓展类
    static final NotifyHander notifyHander = new NotifyHander(smartQQClient);


    public static void main(String[] args) {
        logger.debug("smartqq");

        // 执行登录
        // login 接口在得到登录二维码时会调用CallBackListener
        // 并且二维码byte[] 数据会通过ListenerAction.data返回
        smartQQClient.login(true, listenerAction -> {
            try {
                ImageIO.write((BufferedImage) listenerAction.getData(), "png",
                        new File("/Users/iHelin/Downloads/qrcode.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, listenerAction -> System.out.println("登录成功"));

        GroupsList groupsList = smartQQClient.getGroupsList(true);
        FriendsList friendsList = smartQQClient.getFriendsList(true);
        System.out.println(JSON.toJson(groupsList.getGroups()));
        Map<Long, GroupInfo> groupInfoMap = groupsList.getGroups();
        for (Map.Entry<Long, GroupInfo> groupInfoEntry : groupInfoMap.entrySet()) {
            if (groupInfoEntry.getValue().getName().equals("一点点学习小组")) {
                long gid = groupInfoEntry.getKey();
                SendMsg sendMsg = new SendMsg(gid, MsgType.group_message, "你们好吗");
                smartQQClient.sendMsg(sendMsg);
                System.out.println("发送群消息");
            }
        }

        Map<Long, FriendInfo> friendsInfoMap = friendsList.getFriendsInfo();
        for (Map.Entry<Long, FriendInfo> friendInfoEntry : friendsInfoMap.entrySet()) {
            if (friendInfoEntry.getValue().getNickName().equals("Ian")) {
                long uin = friendInfoEntry.getKey();
                SendMsg sendMsg = new SendMsg(uin, MsgType.message, "你好");
                smartQQClient.sendMsg(sendMsg);
                System.out.println("发送个人消息");
            }
        }

        System.out.println(JSON.toJson(friendsList.getFriendsInfo()));
//        SendMsg sendMsg1 = new SendMsg(4101871856L, MsgType.message, "你们好吗");
//        smartQQClient.sendMsg(sendMsg1);

        DiscusList discusList = smartQQClient.getDiscusList(true);

        System.out.println(JSON.toJson(discusList.getDiscus()));
//        SendMsg sendMsg2 = new SendMsg(203249925L, MsgType.discu_message, "大家好");
//        smartQQClient.sendMsg(sendMsg2);

        // 然后通过手机QQ扫描登录二维码,允许登录后smartqq-agreement-core工具就正常接收信息了
        // 可以通过SmartQQClient.sendMsg向讨论组或者好友或者群组发送信息
        // smartqq-agreement-core工具在得到好友|讨论组|群组信息后就会调用上面提到的NotifyListener.hander
        // 自此你自需要拓展自己的回复消息的内容,就可以自定义自己的QQ机器人或者组件服务拉
    }
}
