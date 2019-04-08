package com.imooc.miaosha_1.redis;

public class UserKey extends BasePrefix{

	private UserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		// TODO Auto-generated constructor stub
	}
	private UserKey(String prefix) {
		super(prefix);
		// TODO Auto-generated constructor stub
	}
	public static UserKey getById = new UserKey("id");
	
	public static UserKey getByName = new UserKey("name");
}
