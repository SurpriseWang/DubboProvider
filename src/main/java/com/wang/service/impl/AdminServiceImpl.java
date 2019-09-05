package com.wang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wang.mappers.AdminMapper;
import com.wang.pojo.Admin;
import com.wang.pojo.AdminExample;
import com.wang.service.AdminService;

/**
 * @author SurpriseWang
 * @date 2019年9月3日上午9:05:28
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public List<Admin> queryAllAdmin() {
		AdminExample example = new AdminExample();
		example.createCriteria().andIdIsNotNull();
		return adminMapper.selectByExample(example);
	}

	@Override
	public Boolean queryAdmin(String adminname, String password) {
		AdminExample example = new AdminExample();
		example.createCriteria().andAdminnameEqualTo(adminname).andPasswordEqualTo(password);
		List<Admin> admin = adminMapper.selectByExample(example);
		if (admin.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
