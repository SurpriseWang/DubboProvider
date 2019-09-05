package com.wang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wang.mappers.BannerMapper;
import com.wang.pojo.Banner;
import com.wang.pojo.BannerExample;
import com.wang.service.BannerService;

/**
 * @author SurpriseWang
 * @date 2019年8月30日下午7:21:16
 */
@Service
public class BannerServiceImpl implements BannerService {
	@Autowired
	private BannerMapper bannerMapper;

	@Override
	public List<Banner> queryAllBanner() {
		BannerExample bannerExample = new BannerExample();
		bannerExample.createCriteria().andIdIsNotNull();
		List<Banner> banners = bannerMapper.selectByExample(bannerExample);
		return banners;
	}

}
