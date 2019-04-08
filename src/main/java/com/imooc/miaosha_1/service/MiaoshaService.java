package com.imooc.miaosha_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.miaosha_1.bean.Goods;
import com.imooc.miaosha_1.bean.OrderInfo;
import com.imooc.miaosha_1.bean.miaoshaUser;
import com.imooc.miaosha_1.dao.GoodsDao;
import com.imooc.miaosha_1.vo.GoodsVo;

@Service
public class MiaoshaService {
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Transactional
	public OrderInfo miaosha(miaoshaUser user, GoodsVo goods) {
		//减库存
		goodsService.reduceStock(goods);
		//下单
		return orderService.createOrder(user,goods);
		
		
	}

}
