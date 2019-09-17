package com.wang.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author SurpriseWang
 * @date 2019年9月3日上午9:17:56
 */
public class Provider {		
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		applicationContext.start();
		System.out.println("~~ProviderStartUp");
		System.in.read();
	} 
}
