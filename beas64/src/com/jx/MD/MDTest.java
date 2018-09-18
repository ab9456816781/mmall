package com.jx.MD;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.JCEMac.MD5;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * ��ϢժҪ�㷨   ����ѧϰ2
 * @author Administrator
 *
 */

/**
 * MD : 消息摘要
 * SHA : 消息散列
 * MAC :
 *
 */
public class MDTest {

	private static  String str = "hello world";
	
	public static void main(String[] args) {
//		jdkMD5();
		jdkMD2();
//		bcmd5();
//		bcMD4();
		bcMD2();
//		bc2jdkmd4();
//		ccMD5();
		ccMD2();
//		jdkSHA1();
//		ccSHA1();
//		jdkHmacMD5();
	}
	
	/** ʹ�� jdk ���� MD5**/
	/**
	 * 128位摘要信息  单向加密
	 *
	 *  JDK只提供了 MD5 md2 的方法  没有MD4
	 */
	public static void jdkMD5 () {
		try {
			MessageDigest mD = MessageDigest.getInstance("MD5");
			byte[] digest = mD.digest(str.getBytes());//得到数组 ， 转为16进制
			String encoderString = Hex.encodeHexString(digest);//使用第三方包  将字符数组转为16进制字符串
			System.out.println("jdk md5 encoder String  :" + encoderString);
		} catch (NoSuchAlgorithmException e) {
		}
	}
	
	public static void jdkMD2() {
		try {
			MessageDigest MD = MessageDigest.getInstance("MD2");
			byte[] digest = MD.digest(str.getBytes());
			String encodeHexString = Hex.encodeHexString(digest);
			System.out.println("jdk md2 encoder string :" + encodeHexString); 
		} catch (NoSuchAlgorithmException e) {
		}
	}
	
	//����ʹ�أ�
	public static void bcmd5() {
		MD5Digest digest = new MD5Digest();
		digest.update(str.getBytes() , 0 , str.getBytes().length);
		byte[] md5byte = new byte[digest.getDigestSize()];
		digest.doFinal(md5byte, 0);
		//org.bouncycastle.util.encoders.Hex.toHexString  同一个包下的  将字符数组 转为16进制字符串
		System.out.println("bouncy castle MD5:" + Hex.encodeHexString(md5byte));
	}

	public static void bcMD4(){
		Digest digest = new MD4Digest();
		digest.update(str.getBytes() , 0 , str.length());
		byte[] bytes = new byte[digest.getDigestSize()];
		digest.doFinal(bytes , 0);
		System.out.println("bc md4 :" + Hex.encodeHexString(bytes));
	}

	public static void bcMD2(){
		Digest digest = new MD2Digest();
		digest.update(str.getBytes() , 0 , str.length());
		byte[] md2Bytes = new byte[digest.getDigestSize()];
		digest.doFinal(md2Bytes , 0) ;
		System.out.println("BC  md2 ::" + Hex.encodeHexString(md2Bytes));
	}
	
	//��bouncy castle��jdk���ʵ��:MD4
	public static void bc2jdkmd4() {
		Security.addProvider(new BouncyCastleProvider());//给JDK动态添加
		try {
			MessageDigest md = MessageDigest.getInstance("MD4");
			byte[] digest = md.digest(str.getBytes());
			String encodeHexString = Hex.encodeHexString(digest);
			System.out.println("bc2jdkmd4  : "+ encodeHexString);
		} catch (NoSuchAlgorithmException e) {
			
		}
	}
	
	
	// ��common codesʵ��ʵ��:MD5
	public static void ccMD5() {
		System.out.println("common codes MD5:" + DigestUtils.md5Hex(str.getBytes()));
	}
 
	// ��common codesʵ��ʵ��:MD2
	public static void ccMD2() {
		System.out.println("common codes MD2:" + DigestUtils.md2Hex(str.getBytes()));
	}
	
	//jdk SHA1
	public static void jdkSHA1() {
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA");
			mDigest.update(str.getBytes());
			String encodeHexString = Hex.encodeHexString(mDigest.digest());
			System.out.println("jdk SHA1 : " + encodeHexString);
		} catch (NoSuchAlgorithmException e) {
		}
	}
	
	// ��common codesʵ��ʵ��:SHA1
	public static void ccSHA1() {
		//byte[]���鷽ʽ
		System.out.println("common codes SHA1 - 1 :" + DigestUtils.sha1Hex(str.getBytes()));
		//String��ʽ
		System.out.println("common codes SHA1 - 2 :" + DigestUtils.sha1Hex(str));
	}

	

	 
	// ��jdkʵ��:
	public static void jdkHmacMD5() {
		try {
			// ��ʼ��KeyGenerator
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
			// ������Կ
			SecretKey secretKey = keyGenerator.generateKey();
			// ��ȡ��Կ
			// byte[] key = secretKey.getEncoded();
			byte[] key = Hex.decodeHex(new char[] { '1', '2', '3', '4', '5',
					'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e' });
 
			// ��ԭ��Կ��HmacMD5���㷨������
			SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacMD5");
			// ʵ����MAC
			Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
			// ��ʼ��MAC
			mac.init(restoreSecretKey);
			// ִ����ϢժҪ
			byte[] hmacMD5Bytes = mac.doFinal(str.getBytes());
			System.out.println("jdk hmacMD5:"
					+ Hex.encodeHexString(hmacMD5Bytes));
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ��bouncy castleʵ��:
	public static void bcHmacMD5() {
		HMac hmac = new HMac(new MD5Digest());
		// ������16���Ƶ��ַ������ȱ�����2�ı���
		hmac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("123456789abcde")));
		hmac.update(str.getBytes(), 0, str.getBytes().length);
 
		// ִ��ժҪ
		byte[] hmacMD5Bytes = new byte[hmac.getMacSize()];
		hmac.doFinal(hmacMD5Bytes, 0);
 
	}

	
}





























