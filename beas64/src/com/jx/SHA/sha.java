package com.jx.SHA;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class sha {

    private static String str = "True, good, beautiful";

    public static void main(String[] args) {
//        jdksha1();
//        bcsha1();
//        bcsha256();
        bcsha224_1();
    }

    /**
     * jdk 实现 sha1
     */
    public static void jdksha1(){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(str.getBytes());
            System.out.println("md  sha1 :" + Hex.encodeHexString(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * bc 实现 sha1
     */
    public static void bcsha1(){
        Digest d = new SHA1Digest();
        d.update(str.getBytes() , 0 , str.length());
        byte[] sha1Digest = new byte[d.getDigestSize()];
        d.doFinal(sha1Digest,0);
        System.out.println("bc sha1 :" + Hex.encodeHexString(sha1Digest));
    }

    public static void bcsha256(){
        Digest digest =  new SHA256Digest();
        digest.update(str.getBytes() , 0 , str.length());
        byte[] sha256Digest = new byte[digest.getDigestSize()];
        digest.doFinal(sha256Digest , 0);
        System.out.println("bc sha256 : " + Hex.encodeHexString(sha256Digest));
    }

    public static void bcsha224_1(){
        Security.addProvider(new BouncyCastleProvider());

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] sha224Digest = md.digest(str.getBytes());
            System.out.println("bc sha224- 1  :" + Hex.encodeHexString(sha224Digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void ccsha1(){

    }


}
