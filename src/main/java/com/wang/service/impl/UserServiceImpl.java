package com.wang.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wang.mappers.UserMapper;
import com.wang.pojo.User;
import com.wang.pojo.UserExample;
import com.wang.service.UserService;

/**
 * @author SurpriseWang
 * @date 2019年9月9日上午9:48:31
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryUser(String userName, String password) {
		UserExample example = new UserExample();
		example.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(password);
		List<User> users = userMapper.selectByExample(example);
		return users.get(0);
		
	}

	@Override
	public Boolean createUser(User user) {
		user.setCreateTime(new Date(System.currentTimeMillis()));
		user.setUpdateTime(new Date(System.currentTimeMillis()));
		user.setIsDelete(0);
		int insert = userMapper.insert(user);
		if (insert == 1) {
			return true;
		} else {
			return false;
		}
	}
}
