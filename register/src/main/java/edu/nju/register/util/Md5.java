package edu.nju.register.util;


import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dongyibo on 2017/11/15.
 */
public class Md5 {

    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        Base64 base64en = new Base64();
        //加密后的字符串
        byte[] newBytes = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return new String(newBytes);
    }
}

