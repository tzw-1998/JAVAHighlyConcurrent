package com.imooc.miaosha_1.redis;

public interface KeyPrefix {
	
	//有效期
	public int expireSeconds();
	
	//key前缀
	public String getPrefix();
}
