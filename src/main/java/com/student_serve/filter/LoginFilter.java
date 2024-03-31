package com.student_serve.filter;

import com.student_serve.common.ErrorCode;
import com.student_serve.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.student_serve.constant.UserConstant.USER_LOGIN_STATE;
import static com.student_serve.constant.UserConstant.allowURL;

@RequestMapping("/archive")
@WebFilter(urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("进行过滤");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();// /backend/index.html

        log.info("拦截到请求：{}", requestURI);

        if (request.getSession().getAttribute(USER_LOGIN_STATE) != null) {
            // 已登录
            log.info("用户已登录");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 未登录
        log.info("用户未登录");

        // register login 其他允许进入
        List<String> li = List.of(requestURI.split("/"));
        int index = allowURL.indexOf(li.get(li.size() - 1));
        if (index != -1) {
            // 允许通过
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR,"未登录,请重新登录");
        }
//
    }

    @Override
    public void destroy() {
        log.info("过滤器销毁");
    }
}
