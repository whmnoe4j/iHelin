package me.ianhe.wechat.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.enums.parameters.BaseParaEnum;
import me.ianhe.wechat.utils.MyHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核心存储类，全局只保存一份，单例模式
 *
 * @author iHelin
 * @since 2017/12/11 14:28
 */
public class Core {

    private static volatile Core instance;

    private Core() {

    }

    /**
     * 终极版本：volatile，史上最安全的单例吧
     *
     * @author iHelin
     * @since 2017/12/11 10:01
     */
    public static Core getInstance() {
        if (instance == null) {
            synchronized (Core.class) {
                if (instance == null) {
                    instance = new Core();
                }
            }
        }
        return instance;
    }

    private volatile Boolean alive = false;
    private int memberCount = 0;

    private String indexUrl;
    private String userName;
    private String nickName;
    private List<BaseMsg> msgList = new ArrayList<>();

    /**
     * 登录账号自身信息
     */
    private JSONObject userSelf;
    /**
     * 好友+群聊+公众号+特殊账号
     */
    private List<JSONObject> memberList = new ArrayList<>();
    /**
     * 联系人列表
     */
    private List<JSONObject> contactList = new ArrayList<>();
    /**
     * 群
     */
    private List<JSONObject> groupList = new ArrayList<>();
    /**
     * 群聊成员字典
     */
    private Map<String, JSONArray> groupMemeberMap = new HashMap<>();
    /**
     * 公众号／服务号
     */
    private List<JSONObject> publicUsersList = new ArrayList<>();
    /**
     * 特殊账号
     */
    private List<JSONObject> specialUsersList = new ArrayList<>();
    /**
     * 群ID列表
     */
    private List<String> groupIdList = new ArrayList<>();
    /**
     * 群NickName列表
     */
    private List<String> groupNickNameList = new ArrayList<>();

    private Map<String, JSONObject> userInfoMap = new HashMap<>();

    Map<String, Object> loginInfo = new HashMap<>();
    MyHttpClient myHttpClient = MyHttpClient.getInstance();
    String uuid = null;

    boolean useHotReload = false;
    String hotReloadDir = "itchat.pkl";
    int receivingRetryCount = 5;

    /**
     * 最后一次收到正常retcode的时间，秒为单位
     */
    private long lastNormalRetcodeTime;

    /**
     * 请求基础参数
     */
    public Map<String, Object> getBaseParamMap() {
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        for (BaseParaEnum baseRequest : BaseParaEnum.values()) {
            map.put(baseRequest.getParam(), getLoginInfo().get(baseRequest.getValue()).toString());
        }
        paramMap.put("BaseRequest", map);
        return paramMap;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public List<JSONObject> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<JSONObject> memberList) {
        this.memberList = memberList;
    }

    public Map<String, Object> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(Map<String, Object> loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isUseHotReload() {
        return useHotReload;
    }

    public void setUseHotReload(boolean useHotReload) {
        this.useHotReload = useHotReload;
    }

    public String getHotReloadDir() {
        return hotReloadDir;
    }

    public void setHotReloadDir(String hotReloadDir) {
        this.hotReloadDir = hotReloadDir;
    }

    public int getReceivingRetryCount() {
        return receivingRetryCount;
    }

    public void setReceivingRetryCount(int receivingRetryCount) {
        this.receivingRetryCount = receivingRetryCount;
    }

    public MyHttpClient getMyHttpClient() {
        return myHttpClient;
    }

    public List<BaseMsg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<BaseMsg> msgList) {
        this.msgList = msgList;
    }

    public void setMyHttpClient(MyHttpClient myHttpClient) {
        this.myHttpClient = myHttpClient;
    }

    public List<String> getGroupIdList() {
        return groupIdList;
    }

    public void addGroupId(String groupId) {
        groupIdList.add(groupId);
    }

    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public List<JSONObject> getContactList() {
        return contactList;
    }

    public void setContactList(List<JSONObject> contactList) {
        this.contactList = contactList;
    }

    public List<JSONObject> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<JSONObject> groupList) {
        this.groupList = groupList;
    }

    public List<JSONObject> getPublicUsersList() {
        return publicUsersList;
    }

    public void setPublicUsersList(List<JSONObject> publicUsersList) {
        this.publicUsersList = publicUsersList;
    }

    public List<JSONObject> getSpecialUsersList() {
        return specialUsersList;
    }

    public void setSpecialUsersList(List<JSONObject> specialUsersList) {
        this.specialUsersList = specialUsersList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public JSONObject getUserSelf() {
        return userSelf;
    }

    public void setUserSelf(JSONObject userSelf) {
        this.userSelf = userSelf;
    }

    public Map<String, JSONObject> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, JSONObject> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

    public synchronized long getLastNormalRetcodeTime() {
        return lastNormalRetcodeTime;
    }

    public synchronized void setLastNormalRetcodeTime(long lastNormalRetcodeTime) {
        this.lastNormalRetcodeTime = lastNormalRetcodeTime;
    }

    public List<String> getGroupNickNameList() {
        return groupNickNameList;
    }

    public void setGroupNickNameList(List<String> groupNickNameList) {
        this.groupNickNameList = groupNickNameList;
    }

    public Map<String, JSONArray> getGroupMemeberMap() {
        return groupMemeberMap;
    }

    public void setGroupMemeberMap(Map<String, JSONArray> groupMemeberMap) {
        this.groupMemeberMap = groupMemeberMap;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

}
