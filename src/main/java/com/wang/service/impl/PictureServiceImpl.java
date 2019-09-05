package com.wang.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wang.mappers.ItemMapper;
import com.wang.mappers.PictureMapper;
import com.wang.pojo.Item;
import com.wang.pojo.ItemExample;
import com.wang.pojo.Picture;
import com.wang.pojo.PictureExample;
import com.wang.service.PictureService;

/**
 * @author SurpriseWang
 * @date 2019年8月30日下午4:38:41
 */
@Service
public class PictureServiceImpl implements PictureService {
	@Autowired
	private PictureMapper picturemapper;
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public List<Picture> queryPictures(Integer categoryId) {
		ItemExample itemExample = new ItemExample();
		itemExample.createCriteria().andCategoryIdEqualTo(categoryId);
		List<Item> items = itemMapper.selectByExample(itemExample);
		List<Integer> itemIds = new ArrayList<Integer>();
		for (Item item : items) {
			itemIds.add(item.getId());
		}
		List<Picture> pictureses = new ArrayList<Picture>();
		for (Integer itemId : itemIds) {
			PictureExample pictureExample = new PictureExample();
			pictureExample.createCriteria().andItemIdEqualTo(itemId);
			List<Picture> selectByExample = picturemapper.selectByExample(pictureExample);
			if(!selectByExample.isEmpty()) {
				pictureses.add(selectByExample.get(0));
			}
		}
		return pictureses;
	}
}
