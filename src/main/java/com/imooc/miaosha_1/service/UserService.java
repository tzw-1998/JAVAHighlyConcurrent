package com.imooc.miaosha_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.miaosha_1.bean.User;
import com.imooc.miaosha_1.dao.UserDao;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	public User getById(int id) {
		return userDao.getById(id);
	}
	@Transactional
	public boolean tx() {
		// TODO Auto-generated method stub
		//验证事务
		User user1 = new User();
		user1.setId(2);
		user1.setName("2222");
		userDao.insert(user1);
		User user2 = new User();
		user1.setId(3);
		user1.setName("2222");
		userDao.insert(user2);
		return true;
	}

}
