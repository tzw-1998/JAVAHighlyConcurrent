package com.imooc.miaosha_1.vo;

import javax.validation.constraints.NotNull;

import com.imooc.miaosha_1.validator.IsMobile;

public class LoginVo {
	
	
	@NotNull
	@IsMobile
	private String mobile;
	
	@NotNull
	private String password;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
