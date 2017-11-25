package com.warrior.util.shiro;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5 {

    public static String ALGORITHM_NAME = "md5";
    public static int HASHITERATIONS = 3;

    /**
     * 获取加密字符串
     * @param str
     * @param salt
     * @return
     */
    public static String genMd5(String str,String salt){
        SimpleHash hash = new SimpleHash(ALGORITHM_NAME,str,salt,HASHITERATIONS);
        return hash.toHex();
    }

    /**
     * 产生随机加密因子
     * @return
     */
    public static String genSalt(){
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    public static void main(String args[]){
        String salt = MD5.genSalt();
        String pwd = MD5.genMd5("123456","admin"+salt);
        System.out.println(salt);
        System.out.println(pwd);
    }
}