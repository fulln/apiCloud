package com.fulln.me.config.enums;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.config.EnumsConfig;
import com.fulln.me.service.basic.ILanguageCloumService;


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
    QUERY_EMPTY(1001, "common.query.empty", null),
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

    ;

    public int code;
    public String message;
    public Object datas;

    private ILanguageCloumService cloumService;

    public void setCloumService(ILanguageCloumService cloumService) {
        this.cloumService = cloumService;
    }

    GlobalEnums(int code, String message, Object datas) {
        this.code = code;
        this.message = message;
        this.datas = datas;
    }

    public GlobalResult results(Object... value) {
        GlobalResult re = results();
        re.setDatas(value);
        return re;
    }

    public GlobalResult results() {
        GlobalResult re = new GlobalResult();
        re.setCode(this.code);
        re.setMessage(cloumService.getCloums(message));
        re.setDatas(this.datas);
        return re;
    }

}
