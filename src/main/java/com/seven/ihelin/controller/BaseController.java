package com.seven.ihelin.controller;

import com.seven.ihelin.manager.AccessTokenManager;
import com.seven.ihelin.manager.AdviceManager;
import com.seven.ihelin.manager.UserManager;

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


    protected static final int PAGE_LENGTH = 20;//分页大小
    protected static final int MAX_LENGTH = 1000;

}
