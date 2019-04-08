package com.imooc.miaosha_1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imooc.miaosha_1.bean.miaoshaUser;

@Mapper
public interface miaoshaUserDao {
	
	@Select("select * from miaosha_user where id = #{id}")
	public miaoshaUser getById(@Param("id")long id);
}
