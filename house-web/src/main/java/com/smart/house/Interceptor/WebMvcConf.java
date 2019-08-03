package com.smart.house.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConf implements WebMvcConfigurer {

	@Autowired
	private AuthActionInterceptor authActionInterceptor;
	
	@Autowired
	private AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		 registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");  //释放静态拦截
		 registry.addInterceptor(authActionInterceptor).addPathPatterns("/user/profile")
		 .addPathPatterns("/user/activate");               //路径请求拦截
		    WebMvcConfigurer.super.addInterceptors(registry);
	}


}
