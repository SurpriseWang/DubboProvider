package com.wang.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.pojo.User;
import com.wang.service.ShopingCartService;
import com.wang.vo.ItemVo;
import com.wang.vo.ShopingCartVo;

import redis.clients.jedis.Jedis;

/**
 * @author SurpriseWang
 * @date 2019年9月12日上午8:49:38
 */
@Service
public class ShopingCartServiceImpl implements ShopingCartService {
	@Override
	public String queryShopingCart(String value) {
		try {
			@SuppressWarnings("resource")
			Jedis jedis = new Jedis("localhost", 6379);
			ObjectMapper objectMapper = new ObjectMapper();
			String userShupingCare = null;
			String jsonUser = jedis.get(value);
			User user = objectMapper.readValue(jsonUser, User.class);
			String value1 = jedis.get(user.getId().toString());
			if (value1 != null) {
				List<ShopingCartVo> listShupingCareVo = objectMapper.readValue(value1,
						new TypeReference<List<ShopingCartVo>>() {
						});
				userShupingCare = objectMapper.writeValueAsString(listShupingCareVo);
				if (userShupingCare.isEmpty()) {
					userShupingCare = objectMapper.writeValueAsString(null);
				}
			} else {
				userShupingCare = objectMapper.writeValueAsString(null);
			}
			return userShupingCare;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String insertItemInShopingCart(String callback, String value, Integer id, ItemVo itemVo) {
		try {
			@SuppressWarnings("resource")
			Jedis jedis = new Jedis("localhost", 6379);
			String jsonUser = jedis.get(value);
			ObjectMapper objectMapper = new ObjectMapper();
			User user = objectMapper.readValue(jsonUser, User.class);
			String value1 = jedis.get(user.getId().toString());
			if (value1 == null) {
				ShopingCartVo shopingCartVo = new ShopingCartVo();
				shopingCartVo.setItem(itemVo.getItem());
				shopingCartVo.setNumber(1);
				shopingCartVo.setPicture(itemVo.getPictures().get(0));
				List<ShopingCartVo> listShupingCareVo = new ArrayList<ShopingCartVo>();
				listShupingCareVo.add(shopingCartVo);
				String listShupingCareVoAsString = objectMapper.writeValueAsString(listShupingCareVo);
				jedis.set(user.getId().toString(), listShupingCareVoAsString);
				return callback + "(robackFunction(" + true + "))";
			} else {
				List<ShopingCartVo> listShopingCareVo = objectMapper.readValue(value1,
						new TypeReference<List<ShopingCartVo>>() {
						});
				Integer idNumber = 0;
				for (ShopingCartVo shopingCartVo : listShopingCareVo) {
					Integer itemID = shopingCartVo.getItem().getId();
					if (itemID == id) {
						Integer number = shopingCartVo.getNumber();
						shopingCartVo.setNumber(number += 1);
						idNumber += 1;
					}
				}
				if (idNumber == 0) {

					ShopingCartVo shopingCartVo = new ShopingCartVo();
					shopingCartVo.setItem(itemVo.getItem());
					shopingCartVo.setNumber(1);
					shopingCartVo.setPicture(itemVo.getPictures().get(0));
					listShopingCareVo.add(shopingCartVo);
				}
				String listShupingCareVoAsString = objectMapper.writeValueAsString(listShopingCareVo);
				jedis.set(user.getId().toString(), listShupingCareVoAsString);
				return callback + "(robackFunction(" + true + "))";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return callback + "(robackFunction(" + false + "))";
	}

	@Override
	public String deleteItemInShopingCart(String value, Integer[] itemId) {
		try {
			@SuppressWarnings("resource")
			Jedis jedis = new Jedis("localhost", 6379);
			String jsonUser = jedis.get(value);
			ObjectMapper objectMapper = new ObjectMapper();
			User user = objectMapper.readValue(jsonUser, User.class);
			String jsonUserShopingCart = jedis.get(user.getId().toString());
			List<ShopingCartVo> userShopingCart = objectMapper.readValue(jsonUserShopingCart,
					new TypeReference<List<ShopingCartVo>>() {
					});
			removeItem: for (ShopingCartVo shopingCartVo : userShopingCart) {
				for (Integer id : itemId) {
					if (shopingCartVo.getItem().getId().equals(id)) {
						userShopingCart.remove(shopingCartVo);
						break removeItem;
					}
				}
			}
			String listShupingCareVoAsString = objectMapper.writeValueAsString(userShopingCart);
			jedis.set(user.getId().toString(), listShupingCareVoAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http://localhost:8083/DubboShopingCart/view/html/shopingcart.html";
	}

	@Override
	public Integer buyChosenItemInShopingCart(String value, Integer[] itemId) {
		Integer monery = 0;
		try {
			Jedis jedis = new Jedis("localhost", 6379);
			String jsonUser = jedis.get(value);
			ObjectMapper objectMapper = new ObjectMapper();
			User user = objectMapper.readValue(jsonUser, User.class);
			String jsonUserShopingCart = jedis.get(user.getId().toString());
			List<ShopingCartVo> userShopingCart = objectMapper.readValue(jsonUserShopingCart,
					new TypeReference<List<ShopingCartVo>>() {
					});
			removeItem: for (ShopingCartVo shopingCartVo : userShopingCart) {
				for (Integer id : itemId) {
					if (shopingCartVo.getItem().getId().equals(id)) {
						monery += Integer.valueOf(shopingCartVo.getItem().getPrice())
								* Integer.valueOf(shopingCartVo.getNumber());
						userShopingCart.remove(shopingCartVo);
						break removeItem;
					}
				}
			}
			String listShupingCareVoAsString = objectMapper.writeValueAsString(userShopingCart);
			jedis.set(user.getId().toString(), listShupingCareVoAsString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monery;
	}

}
