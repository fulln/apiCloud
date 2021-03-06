package com.fulln.me.api.common.enums;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.config.EnumsConfig;
import com.fulln.me.api.common.i18n.LanguageFieldService;


/**
 * @AUthor: fulln
 * @Description: 返回结果枚举
 * @Date : Created in  18:35  2018/8/4.
 */
public enum GlobalEnums implements EnumsConfig {
    /**
     * 系统异常
     */
    SYS_ERROR(-9999, "common.sys.error", null),
    PLATFORM_ERROR(-9001, "common.platform.error", null),
    TIME_OUT_ERROR(-9000, "common.time.out.error", null),
    QUERY_FAIL(-1000, "common.query.fail", null),
    GET_LUCK_PART(1000, "common.get.luck.part", null),
    NOT_LUCK_PACKAGE(-1001, "common.not.luck.package", null),
    VALIDATE_ERROR(-1002, "common.validate.error", null),
    QUERY_EMPTY(-999, "common.query.empty", null),
    QUERY_SUCCESS(1002, "common.query.success", null),
    INSERT_SUCCESS(1003, "common.insert.success", null),
    INSERT_ERROR(-1003, "common.insert.error", null),
    UPDATE_SUCCESS(1004, "common.update.success", null),
    UPDATE_ERROR(-1004, "common.update.error", null),
    READ_SUCCESS(1005, "common.read.success", null),
    READ_ERROR(-1005, "common.read.error", null),
    FILE_EXIST(-1006, "common.file.exist", null),
    FILE_NOT_EXIST(-1007, "common.file.not.exist", null),
    DELETE_SUCCESS(1008, "common.delete.success", null),
    DELETE_ERROR(-1008, "common.delete.error", null),
    EMPTY_PARAMETER(-1009, "common.empty", null),
    ROLE_IS_USED(-1010, "role.account.used", null),
    TOKEN_EXCEPTION(-1011, "token.error", null),
    MESSAGE_SEND_SUCCESS(1011, "sms.send.success", null),
    MESSAGE_SEND_EXCEPTION(-1011, "sms.send.error", null),
    MESSAGE_VERIFY_SUCCESS(1012, "sms.verify.success", null),
    MESSAGE_VERIFY_EXCEPTION(-1012, "sms.verify.error", null),
    MESSAGE_SEND_EXISTS_EXCEPTION(1013, "sms.send.verify.code", null),
    UPLOAD_SUCCESS(1014, "common.upload.success", null),
    UPLOAD_ERROR(-1014, "common.upload.error", null),
    DOWNLOAD_SUCCESS(1015, "common.download.success", null),
    DOWNLOAD_ERROR(-1015, "common.download.error", null),
    USER_HAS_NO_PERMISSION(-1016, "user.no.permission", null),
    USER_ALREADY_EXISTS(-1017, "login.message.accountExist", null),
    PARAMETER_EXCEPTION(-1018, "common.empty", null),
    USER_HAS_NO_LOGIN(-1, "user.no.login", ""),
    LOGIN_INPUT_CODE(-2, "login.input.code", ""),
    ERROR_INPUT_CODE(-3, "error.input.code", ""),
    ERROR_INPUT_PASS(-4, "error.input.pass", ""),
    LOGIN_MAX_FAIL(-5, "login.max.fail", ""),
    ERROR_ACCOUNT_LOCK(-6, "error.account.lock", ""),
    ERROR_ACCOUNT_DISPLAY(-7, "error.account.display", ""),
    ERROR_ACCOUNT_UNACTIVATED(-8, "error.account.unactivated", ""),
    LOGIN_UNKNOWN_ACCOUNT(-9, "login.unknown.account", ""),
    ROLE_ACCOUNT_LOCKED(-10, "role.account.locked", ""),
    ROLE_ACCOUNT_DELETE(-11, "role.account.delete", ""),
    LOGIN_SUCCESS(1, "login.success", ""),
    LOGIN_FAIL(-1, "login.fail", ""),
    EMAIL_SUCCESS(12, "email.send.success", ""),
    EMAIL_FAIL(-12, "email.send.error", ""),
    REGISTER_SUCCESS(13, "system.register.success", ""),
    REGISTER_FAIL(-13, "system.register.fail", ""),


    ;

    public int code;
    public String message;
    public Object datas;


    private LanguageFieldService fieldService;


    public void setFieldService(LanguageFieldService fieldService) {
        this.fieldService = fieldService;
    }

    GlobalEnums(int code, String message, Object datas) {
        this.code = code;
        this.message = message;
        this.datas = datas;
    }

    public GlobalResult results(Object value0,Object... value) {
        GlobalResult re = results();
        if (this.code < 0) {
            re.setMessage(value0.toString());
        }
        if (value.length == 1) {
            re.setDatas(value0);
        } else {
            re.setDatas(value);
        }
        return re;
    }

    public GlobalResult results() {
        GlobalResult re = new GlobalResult();
        re.setCode(this.code);
        re.setMessage(fieldService.getField(message));
        re.setDatas(this.datas);
        return re;
    }

}
