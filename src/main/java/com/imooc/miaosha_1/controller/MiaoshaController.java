package com.imooc.miaosha_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imooc.miaosha_1.bean.MiaoshaOrder;
import com.imooc.miaosha_1.bean.OrderInfo;
import com.imooc.miaosha_1.bean.miaoshaUser;
import com.imooc.miaosha_1.result.CodeMsg;
import com.imooc.miaosha_1.service.GoodsService;
import com.imooc.miaosha_1.service.MiaoshaService;
import com.imooc.miaosha_1.service.OrderService;
import com.imooc.miaosha_1.vo.GoodsVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;
	
	@RequestMapping("/do_miaosha")
	public String Miaosha(Model model,miaoshaUser user,@RequestParam("goodsId")long goodsId) {
		
		model.addAttribute("user", user);
		if(user==null) {
			return "login";
		}
		//判断商品库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if(stock==0) {
			model.addAttribute("errmes", CodeMsg.MIAO_SHA_OVER.getMsg());
			return "miaosha_fail";
		}
		//判断是否秒杀到了
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
		if(order!=null) {
			model.addAttribute("errmes", CodeMsg.REPEATE_MIAOSHA);
			return "miaosha_fail";		
		}
		//减库存 下订单 写入秒杀订单
		OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
		model.addAttribute("OrderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}
}
