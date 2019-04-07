package com.fulln.me.api.common.enums;


import com.fulln.me.api.common.enums.config.EnumsConfig;

public enum pythonEnums implements EnumsConfig {
    /**
     * 调用搜索功能py
     */
    SEARCH_TORRENT(1, "site/torrents.py", "搜索功能");

    private String datas;
    private String message;
    private Integer code;

    pythonEnums( Integer code,String datas, String message) {
        this.datas = datas;
        this.message = message;
        this.code = code;
    }

    public String[] getAll(String... text) {
        if (text.length == 0) {
            return null;
        }
        //目标数组
        String[] strings = new String[text.length + 2];
        strings[0] = "python";
        strings[1] = this.datas;
        System.arraycopy(text, 0, strings, 2, text.length);
        return strings;
    }
}