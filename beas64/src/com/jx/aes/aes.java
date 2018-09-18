package com.jx.aes;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

public class aes {

    private static String src = "imooc security AES";

    public static void main(String[] args) {
        jdkAES();
        bcAES();
    }

    public static void jdkAES(){
        try {
            //获取key生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());//根据 加密方式 生成 需要的长度
            //产生key
            SecretKey secretKey = keyGenerator.generateKey();
            //获取key byte数组
            byte[] keyEncoded = secretKey.getEncoded();

            //key转换
            Key key = new SecretKeySpec(keyEncoded, "AES");

            //加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE , key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk aes encrypt: "+ Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE , key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void bcAES(){
        Security.addProvider(new BouncyCastleProvider());

        try {
            //获取key生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES" , "BC");
            keyGenerator.getProvider();
            keyGenerator.init(new SecureRandom());//根据 加密方式 生成 需要的长度
            //产生key
            SecretKey secretKey = keyGenerator.generateKey();
            //获取key byte数组
            byte[] keyEncoded = secretKey.getEncoded();

            //key转换
            Key key = new SecretKeySpec(keyEncoded, "AES");

            //加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE , key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc aes encrypt: "+ Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE , key);
            result = cipher.doFinal(result);
            System.out.println("bc aes decrypt : " + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
