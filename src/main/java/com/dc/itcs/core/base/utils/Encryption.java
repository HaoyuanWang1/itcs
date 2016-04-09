package com.dc.itcs.core.base.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密算法
 * @author lee
 *
 */
public class Encryption {
	private static final String md5Salt = "flmaingoPasswordSalt";
	public static String encrypt(String userId, String password){
		return new Md5Hash(new Md5Hash(password,md5Salt+userId).toBase64(),md5Salt+password).toHex();
	}
	public static void main(String[] args){
		//System.out.println(encrypt("admin","password123"));
		//System.out.println(encrypt("ABCD","password123"));
//		System.out.println(encrypt("limingn","pasword").length());
//		System.out.println(encrypt("limingn","pasword"));
	}
}
