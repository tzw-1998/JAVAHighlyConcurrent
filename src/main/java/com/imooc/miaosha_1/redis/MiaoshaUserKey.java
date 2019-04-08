package com.imooc.miaosha_1.redis;

public class MiaoshaUserKey extends BasePrefix{
	
	public static final int TOKEN_EXPIRE = 3600*24*2;
	public MiaoshaUserKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
		// TODO Auto-generated constructor stub
	}
	
	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
	

}
