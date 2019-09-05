package com.wang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wang.mappers.ItemMapper;
import com.wang.mappers.PictureMapper;
import com.wang.pojo.Item;
import com.wang.pojo.Picture;
import com.wang.pojo.PictureExample;
import com.wang.service.ItemsService;
import com.wang.vo.ItemVo;

@Service
public class ItemsServiceImpl implements ItemsService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private PictureMapper pictureMapper;
	@Override
	public ItemVo queryItems(Integer id) {
		Picture picture = pictureMapper.selectByPrimaryKey(id);
		Integer itemId = picture.getItemId();
		PictureExample pictureExample = new PictureExample();
		pictureExample.createCriteria().andItemIdEqualTo(itemId);
		List<Picture> pictures = pictureMapper.selectByExample(pictureExample);
		Item item = itemMapper.selectByPrimaryKey(itemId);
		ItemVo itemVo = new ItemVo(pictures, item);
		return itemVo;
	}
}
