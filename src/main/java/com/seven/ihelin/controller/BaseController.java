package com.seven.ihelin.controller;

import com.seven.ihelin.manager.AdviceManager;

import javax.annotation.Resource;

/**
 * Created by iHelin on 16/11/1.
 */
public abstract class BaseController {

    @Resource
    protected AdviceManager adviceManager;

    protected static final String NAMESPACE = "admin";
    protected static final String SESSION_KEY_ADMIN = "adminUser";
    protected static final int PAGE_LENGTH = 20;//分页大小
    protected static final int MAX_LENGTH = 1000;

    protected String adminFtl(String ftlFileName) {
        return NAMESPACE + "/" + ftlFileName;
    }

}
