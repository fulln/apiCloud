package me.fulln.base.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @Author: fulln
 * @Description gson转换类
 * @Date: 2018/9/26 0026-11:34
 */
public class GsonUtil {


    private static Gson gson;


    static {
        gson = new Gson();
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        return gson.toJson(object);
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        return gson.fromJson(gsonString, cls);
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        return gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        return gson.fromJson(gsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {

        return gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }


}