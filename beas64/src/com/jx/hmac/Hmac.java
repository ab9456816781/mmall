package com.jx.hmac;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Hmac {

    public static void main(String[] args) {
        jdkHmacMD5();
        bchmacMD5();
    }

    private static String str = "imooc hello~" ;

    public static void jdkHmacMD5(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");//初始化KeyGenerator
            SecretKey secretKey = keyGenerator.generateKey();//产生秘钥
//            byte[] key = secretKey.getEncoded();//获得秘钥
            byte[] key = Hex.decodeHex(new char[]{'1','2','3','4','5','6','7','8','9','a','b','c','d','e'});//获得秘钥

            SecretKeySpec hmacMD5 = new SecretKeySpec(key, "HmacMD5");//还原秘钥
            Mac mac = Mac.getInstance(hmacMD5.getAlgorithm());//实例化 mac
            //初始化mac
            mac.init(hmacMD5);
            //执行消息摘要
            byte[] bytes = mac.doFinal(str.getBytes());
            System.out.println("hmac md5 : " + Hex.encodeHexString(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    public static void bchmacMD5(){
        // 必须是16进制的字符，长度必须是2的倍数
        HMac hMac = new HMac(new MD5Digest());
        hMac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("123456789abcde")));
        hMac.update(str.getBytes() , 0 , str.length());
        //执行摘要
        byte[] bytes = new byte[hMac.getMacSize()];
        hMac.doFinal(bytes , 0);
        System.out.println("bc hmacMD5 ： " +Hex.encodeHexString(bytes));

    }
}
