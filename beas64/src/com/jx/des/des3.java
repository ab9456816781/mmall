package com.jx.des;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

public class des3 {

    private static String str = "imooc security 3DES";

    public static void main(String[] args) {
        jdk3des();
        bc3des();
    }

    public static void jdk3des(){

            try {
                //获取Key生成器
                KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
//                keyGenerator.init(168);//56位
                keyGenerator.init(new SecureRandom());
                //产生key
                SecretKey secretKey =  keyGenerator.generateKey();
                //获取key byte数组
                byte[] byteKey = secretKey.getEncoded();

                //key转换
                DESedeKeySpec desKeySpec = new DESedeKeySpec(byteKey);
                SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");//getInstance()参数指定的加密方式
                //生成秘钥   转换后的key
                Key convertSecretKey  = factory.generateSecret(desKeySpec);

                //加密
                Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");//加解密算法：DES，工作方式：ECB，填充方式：PKCS5Padding
                cipher.init(Cipher.ENCRYPT_MODE , convertSecretKey);//第一个参数是模式：加密模式，KEY:转换后的KEY
                byte[] result = cipher.doFinal(str.getBytes());
                System.out.println("jdk encrypt 3des :" + Hex.encodeHexString(result));

                //解密
                cipher.init(Cipher.DECRYPT_MODE , convertSecretKey);
                result = cipher.doFinal(result);
                System.out.println("jdk decrypt 3des :" + new String(result));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void bc3des(){
            Security.addProvider(new BouncyCastleProvider());

            try {
                //获取key
                KeyGenerator keyGenerator =  KeyGenerator.getInstance("DESede" , "BC");
                keyGenerator.getProvider();
                keyGenerator.init(new SecureRandom());//长度
                //产生key
                SecretKey secretKey = keyGenerator.generateKey();
                //获取key 数组
                byte[] bytesKey = secretKey.getEncoded();

                //key转换
                DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(bytesKey);
                SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
                Key convertSecretKey = factory.generateSecret(deSedeKeySpec);

                //加密
                Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE , convertSecretKey);
                byte[] result= cipher.doFinal(str.getBytes());
                System.out.println(" 加密 3des :" + Hex.encodeHexString(result));
                //解密
                cipher.init(Cipher.DECRYPT_MODE , convertSecretKey);
                result = cipher.doFinal(result);
                System.out.println("decrypt 3des  : " + new String(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
