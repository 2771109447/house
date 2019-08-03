package com.smart.house.Interceptor;

import com.smart.house.model.User;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求
        String reqUri =	request.getRequestURI();
        if (reqUri.startsWith("/static") || reqUri.startsWith("/error") ) {   //释放静态资源请求
            return true;
        }
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("loginUser");       //获取用户
        if (user != null) {
            UserContext.setUser(user);    //将user保存当前线程中
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        //移除当前线程的user变量
        UserContext.remove();

    }


}
