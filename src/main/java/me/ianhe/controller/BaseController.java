package me.ianhe.controller;

import me.ianhe.manager.*;

import javax.annotation.Resource;

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


    protected static final int PAGE_LENGTH = 20;//分页大小
    protected static final int MAX_LENGTH = 1000;

}
