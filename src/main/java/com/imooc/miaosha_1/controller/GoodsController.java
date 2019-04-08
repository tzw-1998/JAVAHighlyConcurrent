package com.imooc.miaosha_1.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha_1.bean.miaoshaUser;
import com.imooc.miaosha_1.result.CodeMsg;
import com.imooc.miaosha_1.result.Result;
import com.imooc.miaosha_1.service.GoodsService;
import com.imooc.miaosha_1.service.MiaoshaUserService;
import com.imooc.miaosha_1.util.ValidatorUtil;
import com.imooc.miaosha_1.vo.GoodsVo;
import com.imooc.miaosha_1.vo.LoginVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	MiaoshaUserService miaoshaUserService;
	
	@Autowired
	GoodsService goodsService;
	
	private static Logger log = LoggerFactory.getLogger(GoodsController.class);
	
	@RequestMapping("/to_list")
	public String toList(HttpServletResponse response,Model model,@CookieValue(value=MiaoshaUserService.COOKI_NAME_TOKEN,required=false)String cookieToken,
			@RequestParam(value=MiaoshaUserService.COOKI_NAME_TOKEN,required=false)String paramToken) {

		if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)) {
			return "login";
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		miaoshaUser user = miaoshaUserService.getByToken(response,token);
		model.addAttribute("user", user);
		//查询商品列表
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";
	}
	
	@RequestMapping("")
	public String GoodsList(Model model) {

		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";
	}
	
	@RequestMapping("/to_detail/{goodsId}")
	public String detail(Model model,@PathVariable("goodsId")long goodsId) {
		
		//一般不用自增id
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods",goods);
		
		//秒杀开始时间
		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();
		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if(now<startAt) {
			//秒杀未开始
			miaoshaStatus = 0;

			remainSeconds =(int)((now-startAt)/1000);
		}else if(now>endAt){
			//秒杀结束
			miaoshaStatus = 2;
			remainSeconds =-1;
		}else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds =0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
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
