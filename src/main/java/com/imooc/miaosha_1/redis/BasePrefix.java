package com.imooc.miaosha_1.redis;

public abstract class BasePrefix implements KeyPrefix{
	
	private int expireSeconds;
	
	private String prefix;
	
	public BasePrefix(String prefix) {
		this(0,prefix);
	}
	public BasePrefix(int expireSeconds,String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	
	@Override
	public int expireSeconds() {
		//默认0代表永不过期
		// TODO Auto-generated method stub
		return expireSeconds;
	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		String className = getClass().getSimpleName();
		return className+":"+prefix;
	}

}
