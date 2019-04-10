package com.fulln.me.config.enums;


import com.fulln.me.api.service.basic.ILanguageCloumService;

public enum MessageEnums {

    USER_HAS_NO_LOGIN(-1, "user.no.login"),
    LOGIN_INPUT_CODE(-2, "login.input.code"),
    ERROR_INPUT_CODE(-3, "error.input.code"),
    ERROR_INPUT_PASS(-4, "error.input.pass"),
    LOGIN_MAX_FAIL(-5, "login.max.fail"),
    ERROR_ACCOUNT_LOCK(-6, "error.account.lock"),
    ERROR_ACCOUNT_DISPLAY(-7, "error.account.display"),
    ERROR_ACOUNT_UNACTIVATED(-8, "error.account.unactivated"),
    LOGIN_UNKNOWN_ACCOUNT(-9, "login.unknown.account"),
    ROLE_ACCOUNT_LOCKED(-10, "role.account.locked"),
    ROLE_ACCOUNT_DELETE(-11, "role.account.delete"),
    ;


    private ILanguageCloumService cloumService;

    private Integer num;
    private String code;


    public void setCloumService(ILanguageCloumService cloumService) {
        this.cloumService = cloumService;
    }

    MessageEnums(Integer num, String code) {
        this.num = num;
        this.code = code;
    }

    public String getCode() {
        return cloumService.getCloums(code);
    }

    public String getCode(String lang) {
        return cloumService.getCloums(code, lang);
    }


}
