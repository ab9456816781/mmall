package com.jx.base64;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class base64Test {
	private static String str = "hello world" ;
	
	public static void main(String[] args) {
		jdkBase64();
		commonCodesBase64();
		bouncyCastBase64();
	}
	
	/** 使用jdk 实现  **/
	public static void jdkBase64() {
		
		BASE64Encoder encoder = new BASE64Encoder();
		String strEncoder = encoder.encode(str.getBytes());
		System.out.println("jdk  strencoder : " + strEncoder);
		
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] strDecoder = decoder.decodeBuffer(strEncoder);
			String strDenco = new String(strDecoder);
			System.out.println("jdk  strDecoder : " + strDenco);
		} catch (IOException e) {
			System.out.println("decoder 出错");
		}
	}
	
	/** 使用 apache 的 common codes **/
	public static void commonCodesBase64(){
		byte[] encodeByte = Base64.encodeBase64(str.getBytes());
		System.out.println("common codes encoder : " + new String(encodeByte));
		
		byte[] decodeBase64 = Base64.decodeBase64(encodeByte);
		System.out.println("common codes  decoder : " + new String(decodeBase64));
	}
	
	/** 使用 bouncy cast   **/
	public static void bouncyCastBase64() {
		byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(str.getBytes());
		System.out.println("bouncy castle encode:" + new String(encodeBytes));
		
		byte[] dencodeBytes = org.bouncycastle.util.encoders.Base64.decode(encodeBytes);
		System.out.println("bouncy castle decode:" + new String(dencodeBytes));

	}
}
