package com.imooc.miaosha_1.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha_1.bean.miaoshaUser;
import com.imooc.miaosha_1.dao.miaoshaUserDao;
import com.imooc.miaosha_1.exception.GlobalException;
import com.imooc.miaosha_1.redis.MiaoshaUserKey;
import com.imooc.miaosha_1.result.CodeMsg;
import com.imooc.miaosha_1.util.MD5Util;
import com.imooc.miaosha_1.util.UUIDUtil;
import com.imooc.miaosha_1.vo.LoginVo;

@Service
public class MiaoshaUserService {
	
	public static final String COOKI_NAME_TOKEN = "token";
	@Autowired
	miaoshaUserDao miaoshadao;
	
	
	@Autowired
	RedisService redisService;
	
	public miaoshaUser getById(long id) {
		return miaoshadao.getById(id);
	}
	public miaoshaUser getByToken(HttpServletResponse response,String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		miaoshaUser user = redisService.get(MiaoshaUserKey.token, token, miaoshaUser.class);
		//延长有效期 登录后重置Token时长
		if(user!=null)
			addCookie(user,token,response);
		return user;
		
	}
	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo==null)
		{
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass  = loginVo.getPassword();
		//判断手机号是否存在于数据库中
		miaoshaUser user = getById(Long.parseLong(mobile));
		if(user==null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassFormPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//分布式session
		//生成cookie存放到第三方缓存Redis中
		
		String token = UUIDUtil.uuid();
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
		return true;
	}
	
	private void addCookie(miaoshaUser user,String token,HttpServletResponse response) {
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
//	public CodeMsg login(LoginVo loginVo) {
//		if(loginVo==null)
//		{
//			return CodeMsg.SERVER_ERROR;
//		}
//		String mobile = loginVo.getMobile();
//		String formPass  = loginVo.getPassword();
//		//判断手机号是否存在于数据库中
//		miaoshaUser user = getById(Long.parseLong(mobile));
//		if(user==null) {
//			return CodeMsg.MOBILE_NOT_EXIST;
//		}
//		//验证密码
//		String dbPass = user.getPassword();
//		String saltDB = user.getSalt();
//		String calcPass = MD5Util.formPassFormPass(formPass, saltDB);
//		if(!calcPass.equals(dbPass)) {
//			return CodeMsg.PASSWORD_ERROR;
//		}
//		return CodeMsg.SUCCESS;
//	}
	
}
