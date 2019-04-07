package com.fulln.me.api.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Random;

/**
 * @program: api
 * @description: MD5加密
 * @author: fulln
 * @create: 2018-10-31 18:19
 * @Version： 0.0.1
 **/
public class MD5util {

    public static String getMd5Hash(String password, String salt){
        /*password  明文密码
         *username  盐      作料自己定义
         *3                         哈希次数，与shiro次数保持相同
         */
        Md5Hash md5Hash=new Md5Hash(password,salt,2);
        return md5Hash.toString();
    }

    public static String getSalt(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        return salt;
    }
    public static void main(String[] args) {
        System.out.println(getMd5Hash("123456",getSalt()));
    }
}
