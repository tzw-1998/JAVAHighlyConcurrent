package com.imooc.miaosha_1.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.imooc.miaosha_1.bean.miaoshaUser;
import com.imooc.miaosha_1.service.MiaoshaUserService;

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	@Override
	public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest arg2,
			WebDataBinderFactory arg3) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = arg2.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = arg2.getNativeResponse(HttpServletResponse.class);
		
		String paramToken = request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
		String cookieToken = getCookieValue(request,MiaoshaUserService.COOKI_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		return miaoshaUserService.getByToken(response,token);
	}

	private String getCookieValue(HttpServletRequest request, String cookiNameToken) {
		// TODO Auto-generated method stub
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies)
		{
			if(cookie.getName().equals(cookiNameToken)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean supportsParameter(MethodParameter arg0) {
		// TODO Auto-generated method stub
		Class<?> clazz = arg0.getParameterType();
		return clazz==miaoshaUser.class;
	}

}
