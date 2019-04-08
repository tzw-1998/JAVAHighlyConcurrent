package com.imooc.miaosha_1.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha_1.result.CodeMsg;
import com.imooc.miaosha_1.result.Result;
import com.imooc.miaosha_1.service.MiaoshaUserService;
import com.imooc.miaosha_1.util.ValidatorUtil;
import com.imooc.miaosha_1.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}
	
//	@RequestMapping("/do_login")
//	@ResponseBody
//	public Result<Boolean> doLogin(LoginVo loginVo){
//		
//		log.info(loginVo.toString());
//		//参数效验
//		String passinput = loginVo.getPassword();
//		String mobile = loginVo.getMobile();
//		if(StringUtils.isEmpty(passinput)) {
//			return Result.error(CodeMsg.PASSWORD_EMPTY);
//		}
//		if(StringUtils.isEmpty(mobile)) {
//			return Result.error(CodeMsg.MOBILE_EMPTY);
//		}
//		//手机号格式检验
//		if(!ValidatorUtil.isMobile(mobile)) {
//			return Result.error(CodeMsg.MOBILE_ERROR);
//		}
//		CodeMsg cm = miaoshaUserService.login(loginVo);
//		if(cm.getCode()==0) {
//			return Result.success(true);
//		}else {
//			return Result.error(cm);
//		}
//	}
	
	
//	@RequestMapping("/do_login")
//	@ResponseBody
//	public Result<Boolean> doLogin(@Valid LoginVo loginVo){
//		log.info(loginVo.toString());
//		
//		CodeMsg codeMsg = miaoshaUserService.login(loginVo);
//		
//		if(codeMsg.getCode()==0) {
//			return Result.success(true);
//		}else {
//			return Result.error(codeMsg);
//		}
//	}
	
	@RequestMapping("/do_login")
	@ResponseBody
	public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo){
		log.info(loginVo.toString());
		
		miaoshaUserService.login(response,loginVo);
		
		return Result.success(true);

	}

}
