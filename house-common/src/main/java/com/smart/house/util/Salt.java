package com.smart.house.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * MD5加密算法
 */
public class Salt {
    private static  final HashFunction FUNCTION= Hashing.md5();
    private static final String salt="salt";       //盐值
    public static String encryPassword(String password){
        HashCode  hashCode=FUNCTION.hashString(password+salt, Charset.forName("UTF-8"));
        return String.valueOf(hashCode);   //返回hash码作为加密后的密码
    }
}
