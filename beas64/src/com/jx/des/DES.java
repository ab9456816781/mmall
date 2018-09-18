package com.jx.des;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * https://blog.csdn.net/u013991521/article/details/48207171
 */
public class DES {

    private static String str = "imooc security DES";

    public static void main(String[] args) {
        jdkDES();
        bcDES();
    }

    //使用jdk实现
    public static void jdkDES(){

        try {
            //获取Key生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);//56位
            //产生key
            SecretKey secretKey =  keyGenerator.generateKey();
            //获取key byte数组
            byte[] byteKey = secretKey.getEncoded();

            //key转换
            DESKeySpec desKeySpec = new DESKeySpec(byteKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//getInstance()参数指定的加密方式
            //生成秘钥   转换后的key
            Key convertSecretKey  = factory.generateSecret(desKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//加解密算法：DES，工作方式：ECB，填充方式：PKCS5Padding
            cipher.init(Cipher.ENCRYPT_MODE , convertSecretKey);//第一个参数是模式：加密模式，KEY:转换后的KEY
            byte[] result = cipher.doFinal(str.getBytes());
            System.out.println("jdk encrypt des :" + Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE , convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk decrypt des :" + new String(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcDES(){
        Security.addProvider(new BouncyCastleProvider());
        try {

            //获取Key生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES" , "BC");
            keyGenerator.getProvider();
            keyGenerator.init(56);//56位
            //产生key
            SecretKey secretKey = keyGenerator.generateKey();
            //获取key byte数组
            byte[] byteKey = secretKey.getEncoded();

            //key转换
            DESKeySpec desKeySpec = new DESKeySpec(byteKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//getInstance()参数指定的加密方式
            //生成秘钥   转换后的key
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            //加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//加解密算法：DES，工作方式：ECB，填充方式：PKCS5Padding
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);//第一个参数是模式：加密模式，KEY:转换后的KEY
            byte[] result = cipher.doFinal(str.getBytes());
            System.out.println("bc encrypt des :" + Hex.encodeHexString(result));

            //解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("bc decrypt des :" + new String(result));
        }catch (Exception e){

        }
    }

}
