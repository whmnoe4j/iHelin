package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.manager.AccessTokenManager;
import me.ianhe.manager.AdviceManager;
import me.ianhe.manager.ArticleManager;
import me.ianhe.manager.FinanceManager;
import me.ianhe.manager.QrcodeManager;
import me.ianhe.manager.ScoreManager;
import me.ianhe.manager.ServiceMenuMannger;
import me.ianhe.manager.UserManager;
import me.ianhe.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by iHelin on 16/11/1.
 */
public abstract class BaseController {

    @Autowired
    protected AdviceManager adviceManager;
    @Autowired
    protected AccessTokenManager accessTokenManager;
    @Autowired
    protected UserManager userManager;
    @Autowired
    protected ServiceMenuMannger serviceMenuMannger;
    @Autowired
    protected ArticleManager articleManager;
    @Autowired
    protected QrcodeManager qrcodeManager;
    @Autowired
    protected FinanceManager financeManager;
    @Autowired
    protected ScoreManager myScoreManager;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    protected String ftl(String ftlFileName) {
        return ftlFileName;
    }

}
