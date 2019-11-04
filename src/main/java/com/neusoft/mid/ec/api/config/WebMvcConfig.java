package com.neusoft.mid.ec.api.config;

import javax.servlet.MultipartConfigElement;

import org.apache.log4j.Logger;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	private static Logger LOGGEER = Logger.getLogger(WebMvcConfig.class);
	

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/messages");
		return messageSource;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/messages/messages**").addResourceLocations("/resources/");
		
		super.addResourceHandlers(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				// .allowCredentials(true)
				.allowedHeaders("*").allowedMethods("GET", "POST", "PUT", "DELETE").maxAge(3600);
	}

	// 关键，将拦截器作为bean写入配置中
	@Bean
	public WhiteIPInterceptor whiteIPInterceptor() {
		return new WhiteIPInterceptor();
	}

	@Bean
	public AuthInterceptor myAuthInterceptor() {
		return new AuthInterceptor();
	}

	@Bean
	public RocketMQInterceptor myRocketMQInterceptor() {
		return new RocketMQInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		LOGGEER.info("====拦截器启动成功====");
		InterceptorRegistration rocketMQ = registry.addInterceptor(myRocketMQInterceptor()).
				excludePathPatterns("/v2/api-docs")
				.excludePathPatterns("/configuration/**");
		// 先执行白名单拦截器，再执行系统拦截器
		
		// 白名单拦截器，校验请求ip是否在白名单之内
//		InterceptorRegistration whiteIP = registry.addInterceptor(whiteIPInterceptor());
//		// 配置拦截的路径
//		whiteIP.addPathPatterns("/**");
//		// 配置不拦截的路径
//		whiteIP.excludePathPatterns();
		
		
//		// 系统拦截器，校验token
//		InterceptorRegistration ir = registry.addInterceptor(myAuthInterceptor());
//		// 配置拦截的路径
//		ir.addPathPatterns("/**");
//		// 配置不拦截的路径
//		ir.excludePathPatterns("/demo/**");
//		// 登录接口
//		ir.excludePathPatterns("/idcs/**");
//		// 记录日志行为接口
//		ir.excludePathPatterns("/trace/**");
//		// 获取城市信息接口
//		ir.excludePathPatterns("/city/getAll/**");
		

	}

}
