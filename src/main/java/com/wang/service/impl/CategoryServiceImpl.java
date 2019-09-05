package com.wang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wang.mappers.CategoryMapper;
import com.wang.pojo.Category;
import com.wang.pojo.CategoryExample;
import com.wang.service.CategoryService;

/**
 * @author SurpriseWang
 * @date 2019年8月30日上午9:10:17
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public List<Category> queryAllCategorys() {
		CategoryExample categoryExample = new CategoryExample();
		categoryExample.createCriteria().andIdIsNotNull();
		List<Category> categorys = categoryMapper.selectByExample(categoryExample);
		return categorys;
	}
}
