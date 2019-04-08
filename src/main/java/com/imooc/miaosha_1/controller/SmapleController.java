package com.imooc.miaosha_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.miaosha_1.bean.User;
import com.imooc.miaosha_1.redis.UserKey;
import com.imooc.miaosha_1.result.CodeMsg;
import com.imooc.miaosha_1.result.Result;
import com.imooc.miaosha_1.service.RedisService;
import com.imooc.miaosha_1.service.UserService;

@Controller
@RequestMapping("/demo")
public class SmapleController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RedisService redisService;
	
	   @RequestMapping("/")
	   @ResponseBody
	    String home() {
	        return "Hello my World!";
	    }
	   //两种输出结果
	   //1rest api json输出 
	   //2页面 模板
	   @RequestMapping("/hello")
	   @ResponseBody
	    public Result<String> hello() {
	        return Result.success("hello,imooc");
	    }
	   @RequestMapping("/helloError")
	   @ResponseBody
	    public Result<String> helloError() {
	        return Result.error(CodeMsg.SERVER_ERROR);
	    }

	   @RequestMapping("/thymeleaf")
	    public String thymeleaf(Model model) {
		   model.addAttribute("name","baoru");
	        return "hello";
	    }
	   @RequestMapping("/db/get")
	   @ResponseBody
	    public Result<User> dbGet() {
		   
		   	User user = userService.getById(1);
	        return Result.success(user);
	    }

	   @RequestMapping("/db/tx")
	   @ResponseBody
	    public Result<Boolean> dbtx() {
		   	userService.tx();
	        return Result.success(true);
	    }
	   @RequestMapping("/redis/get")
	   @ResponseBody
	    public Result<User> redisGet() {
		   	User user =  redisService.get(UserKey.getById,""+1,User.class);//UserKey:id1
	        return Result.success(user);
	    }
	   @RequestMapping("/redis/set")
	   @ResponseBody
	    public Result<Boolean> redisset() {
		   User user = new User();
		   user.setId(1);
		   user.setName("11111");
		   
		   	boolean ret =  redisService.set(UserKey.getById,""+1,user);

	        return Result.success(ret);
	    }


}
