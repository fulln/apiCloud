package com.fulln.me.api.common.enums;


import com.fulln.me.api.common.utils.FileUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: api
 * @description: 正则的枚举
 * @author: fulln
 * @create: 2018-09-26 16:02
 * @Version： 0.0.1
 **/
public enum RegixEnums {

    /**
     * 数字匹配
     */
    LUCK_NUMBER_PATTERN("[0-9]+"),
    /**
     * 到达最大的次数
     */
    NUMBER_PATTERN("lucky_number=([^&]*)"),
    /**
     * classpath的匹配
     */
    CLASSPATH_PATTERN("com.fulln.me.web.controller.*"),
    /**
     * 文章标题匹配
     */
    ARTICLE_TITLE_PATTERN("title: .*"),
    /**
     * 文章类型匹配
     */
    ARTICLE_TYPE_PATTERN("categories: .*"),
    /**
     * 文章tag匹配
     */
    ARTICLE_TAG_PATTERN("tags:(.|\\n)*?(?=categories)"),
    /**
     * 纯净匹配
     */
    PURE_PATTERN("[\\`!@#$%^&*()+=|{}\\':;,\\[\\].<>/?0-9a-zA-Z:;=\\u4e00-\\u9fa5]+"),
    /**
     * 文章标签截取
     */
    ARTICLE_HEAD_PATTERN("---");

    public String datas;

    RegixEnums(String datas) {
        this.datas = datas;
    }

    public Pattern getPatter() {
        return Pattern.compile(this.datas);
    }

    public String getDatas() {
        return datas;
    }

    /**
     * 截取出正则出来的结果值
     */
    public String[] getResults(String matchers) {
        Matcher matcher = getPatter().matcher(matchers);
        return Stream.of(matcher).filter(Matcher::find).map(Matcher::group).toArray(String[]::new);
    }

    /**
     * 截取2个正则中间的内容(获取文章head)
     */
    public  String getContentResult(String matchers){
        StringBuffer sb = new StringBuffer();
        Matcher matcher = getPatter().matcher(matchers);
        if(matcher.find(1)){
            matcher.appendReplacement(sb,"");
        }
        return sb.toString().replace(this.datas,"");
    }

    /**
     * 截取出正则出来的结果值
     */
    public String[] getPureResults(String matchers) {
        Matcher matcher = getPatter().matcher(matchers);
        List<String> str = Stream.of(matcher).filter(Matcher::find).map(Matcher::group).collect(Collectors.toList());
        if (str.size() == 0) {
            return null;
        } else {
            return str.stream().map(s -> {
                Matcher pureMatcher = Pattern.compile(PURE_PATTERN.getDatas()).matcher(s);
                StringBuffer sb = new StringBuffer();
                pureMatcher.appendTail(sb);
                if (pureMatcher.find()) {
                    return s.replace(pureMatcher.group(), "").trim();
                } else {
                    return null;
                }
            }).filter(Objects::nonNull).toArray(String[]::new);
        }
    }

    /**
     * 正则判定
     */
    public boolean matchs(String matchers) {
        Matcher matcher = getPatter().matcher(matchers);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String code = "---\n" +
                "title: docker部署springboot项目\n" +
                "date: 10/18/2018 11:39:28 AM   \n" +
                "comments: true\n" +
                "tags: \n" +
                " - java\n" +
                " - springboot\n" +
                " - docker \n" +
                "categories: \"运维\"\n" +
                "photos:\n" +
                " - https://source.unsplash.com/random/1920*1080\n" +
                "---\n" +
                "最近微服务化应该是比较火了，docker化springboot项目应该是其中基础了，来写一写docker相关的东西吧。\n" +
                "\n" +
                "### dockerfile的编写";
        String st = ARTICLE_HEAD_PATTERN.getContentResult(code);

        @SuppressWarnings("unchecked")
        Map<String, Object> maps = (Map<String, Object>) FileUtil.getYamlMap(st);


        System.out.println(maps.get("title"));
        System.out.println(maps.get("categories"));
        String codes = maps.get("tags").toString();
        System.out.println(codes.substring(1,codes.length()-1));



    }
}
