package com.seven.ihelin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.seven.ihelin.utils.JSON;

public class TestInterceptor implements HandlerInterceptor {

	// 返回值：表示我们是否需要将当前的请求拦截下来
	// false请求将被终止，true请求继续
	// Object 表示的是被拦截的请求的目标对象
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("执行了preHandle方法");
		System.out.println(JSON.toJson(handler));
		return true;
	}

	// 可通过modelAndView修改显示的视图，或修改发往视图的方法
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("执行了postHandle方法");
		// modelAndView.setViewName("index");

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("执行了afterCompletion方法");
	}

}
