package com.rsa.test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class TestRSA {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		//RSA的公钥和私钥是由KeyPairGenerator生成的，获取KeyPairGenerator的实例后还需要设置其密钥位数
		KeyPairGenerator keyPairGen  = KeyPairGenerator.getInstance("rsa");
		keyPairGen.initialize(1024);

		//公钥和私钥可以通过KeyPairGenerator执行generateKeyPair()后生成密钥对KeyPair，
		//通过KeyPair.getPublic()和KeyPair.getPrivate()来获取

		//// 动态生成密钥对，这是当前最耗时的操作，一般要2s以上
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//公钥
		PublicKey publicKey = keyPair.getPublic();
		byte[] publicKeyData  = publicKey.getEncoded();
		System.out.println("公钥为:" + publicKey);
		System.err.println("公钥还原后:"+TestRSA.getPublicKey(publicKeyData));
		//私钥
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] privateKeyData  = privateKey.getEncoded();
		System.out.println("私钥为:" + privateKey);
		System.err.println("私钥还原后:"+TestRSA.getPrivateKey(privateKeyData));

	}

	//通过byte[]可以再度将公钥或私钥还原出来

	// 通过公钥byte[]将公钥还原，适用于RSA算法
	public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,InvalidKeySpecException {

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// 通过私钥byte[]将公钥还原，适用于RSA算法
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,InvalidKeySpecException {
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory instance = KeyFactory.getInstance("rsa");
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
}
