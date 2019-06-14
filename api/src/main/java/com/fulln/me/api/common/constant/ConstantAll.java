package com.fulln.me.api.common.constant;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 项目中常用到的常量
 * @date 2019/6/12 23:21
 **/
public class ConstantAll {
    /**
     * 用户登陆次数保存时间
     */
    public static final int REDIS_REMINE_TIME = 24*60*60;
    /**
     * 用户邮箱注册保存时间
     */
    public static final int REDIS_REGISITER_REMINE_TIME = 5*60;
    /**
     * 用户失败次数
     */
    public static final String LOGIN_FAIL_COUNTS = "LoginFailCounts:";
    /**
     * 邮件注册
     */
    public static final String EMAIL_FOR_REIGISIT_SUBJECT = "[邮件注册]";
    /**
     * 用户邮件发送计时
     */
    public static final String EMAIL_FOR_REIGISIT_RECIVE_USER = "email_for_reigisit_recive_user:";

}
