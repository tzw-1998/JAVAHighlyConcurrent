package com.imooc.miaosha_1.util;

import org.apache.commons.codec.digest.DigestUtils;
//两次MD5 防止拿到明文密码
public class MD5Util {
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	private static final String salt = "1a2b3c4d";
	
	public static String inputPassFormPass(String inputPass) {
		String str = ""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+
				salt.charAt(4);
		return md5(str);
	}
	//salt存数据库
	public static String formPassFormPass(String formPass,String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+
				salt.charAt(4);
		return md5(str);
	}
	
	public static String inputPassToDbPass(String input,String saltDB) {
		String formPass = inputPassFormPass(input);
		String dbPass = formPassFormPass(formPass,saltDB);
		return dbPass;
	}
}
