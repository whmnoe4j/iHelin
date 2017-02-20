package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.manager.*;
import me.ianhe.utils.JSON;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by iHelin on 16/11/1.
 */
public abstract class BaseController {

    @Resource
    protected AdviceManager adviceManager;
    @Resource
    protected AccessTokenManager accessTokenManager;
    @Resource
    protected UserManager userManager;
    @Resource
    protected ServiceMenuMannger serviceMenuMannger;
    @Resource
    protected ArticleManager articleManager;
    @Resource
    protected QrcodeManager qrcodeManager;
    @Resource
    protected FinanceManager financeManager;


    protected static final int DEFAULT_PAGE_LENGTH = 10;//分页大小
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    protected String success() {
        Map<String, Object> res = Maps.newHashMap();
        res.put("status", SUCCESS);
        return JSON.toJson(res);
    }

    protected <T> String success(T model) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("status", SUCCESS);
        res.put("data", model);
        return JSON.toJson(res);
    }

    protected String error() {
        Map<String, Object> res = Maps.newHashMap();
        res.put("status", ERROR);
        return JSON.toJson(res);
    }

    protected <T> String error(T model) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("status", ERROR);
        res.put("data", model);
        return JSON.toJson(res);
    }

    protected String ftl(String ftlFileName){
        return ftlFileName;
    }

}
