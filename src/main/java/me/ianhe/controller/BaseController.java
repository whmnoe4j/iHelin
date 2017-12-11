package me.ianhe.controller;

import com.google.common.collect.Maps;
import me.ianhe.service.*;
import me.ianhe.utils.JsonUtil;
import me.ianhe.wechat.core.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

/**
 * BaseController
 *
 * @author iHelin
 * @since 2017/10/17 15:28
 */
public abstract class BaseController {

    @Autowired
    protected ArticleService articleService;
    @Autowired
    protected ScoreService scoreService;
    @Autowired
    protected FileService fileService;
    @Autowired
    protected JmsProducerService producerService;
    @Autowired
    protected QrcodeService qrcodeService;
    @Autowired
    protected WebSocket webSocket;
    @Autowired
    protected Global global;
    @Autowired
    protected MessageHandler messageHandler;
    @Autowired
    protected WeChatLoginService weChatLoginService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认分页大小
     */
    protected static final int DEFAULT_PAGE_LENGTH = 10;
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String DATA = "data";
    private static final String TOTAL = "total";

    protected String success() {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        return JsonUtil.toJson(res);
    }

    protected String page(Collection model, Integer count) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        res.put(DATA, model);
        res.put(TOTAL, count);
        return JsonUtil.toJson(res);
    }

    protected <T> String success(T model) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, SUCCESS);
        res.put(DATA, model);
        return JsonUtil.toJson(res);
    }

    protected String error() {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, ERROR);
        return JsonUtil.toJson(res);
    }

    protected <T> String error(T model) {
        Map<String, Object> res = Maps.newHashMap();
        res.put(STATUS, ERROR);
        res.put(DATA, model);
        return JsonUtil.toJson(res);
    }

    protected String ftl(String ftlFileName) {
        return ftlFileName;
    }

}
