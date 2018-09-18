package com.jx.pbe;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class pbe {

    private static String src = "imooc security pbe";

    public static void main(String[] args) {
        jdkPBE();
    }

    public static void jdkPBE(){
        try {
            //初始化盐值
            SecureRandom secureRandom = new SecureRandom();//产生随机数
            byte[] salt = secureRandom.generateSeed(8);

            //口令与密钥
            String password = "fucking day";//定义用户自己输入的口令
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());//口令转化为key
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");///实例化转换为KEY的工厂
            Key key = factory.generateSecret(pbeKeySpec);

            //加密
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);//100为需要迭代的次数

            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk pbe encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
            result = cipher.doFinal(result);
            System.out.println("jdk pbe decrypt:" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
