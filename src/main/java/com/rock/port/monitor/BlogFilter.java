package com.rock.port.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器--单点登录
 * Created by caoqingyuan on 2017/10/19.
 */
@Component
public class BlogFilter implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(BlogFilter.class);
    private static final String verifyUrl="http://127.0.0.1:8081/verify/loginVerify.htm";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("filter before run:"+request.getRequestURL());
        return true;

//        HttpSession session = request.getSession();
//        String token =(String)session.getAttribute("token");
//
//        String loginToken = request.getParameter("token");
//        if(StringUtil.isNotEmpty(loginToken)){
//            token=loginToken;
//        }
//
//        Boolean isValid=true;
//        //验证令牌的有效性
//        if (StringUtil.isNotEmpty(token)) {
//            String url=verifyUrl+"?token="+token;
//            logger.info("验证令牌的有效性url:"+url);
//            byte[] bytes = URLConnection.postBinResource(url, "", "UTF-8", 30);
//            logger.info("response:"+new String(bytes));
//            if("OK".equals(new String(bytes))){
//                session.setAttribute("token",token);
//                return isValid;
//            }
//            isValid=false;
//        }
//
//        Enumeration<String> headerNames = request.getHeaderNames();
//
//        //用户还没有登录或者令牌无效
//        if(!StringUtil.isNotEmpty(token)||!isValid){
//            logger.info("用户还没有登录或者令牌无效");
//            String directUrl = request.getRequestURL().toString();
//            String url = verifyUrl+"?directUrl=" + directUrl;
//            //未登录，请求用户中心
//            response.sendRedirect(url);
//            return false;
//        }
//        return false;
    }

    //出controller之后，在视图渲染之前执行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("filter post run");
    }

    //页面渲染之后调用，一般用于资源清理操作
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
