package com.slot.Common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return attr.getResponse();
    }

    public static HttpSession getSession(boolean gen) { 
        return ContextUtil.getRequest().getSession(gen);
    }

    public static Object getAttrFromRequest(String key) {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return attr.getAttribute(key, ServletRequestAttributes.SCOPE_REQUEST);
    }

    public static void setAttrToRequest(String key, Object obj) {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        attr.setAttribute(key, obj, ServletRequestAttributes.SCOPE_REQUEST);
    }

    public static Object getAttrFromSession(String key) {
    	try {
	        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
	        return attr.getAttribute(key, ServletRequestAttributes.SCOPE_SESSION);
    	}catch(Exception e) { e.printStackTrace(); }
    	return null;
    }

    public static void setAttrToSession(String key, Object obj) {
        ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        attr.setAttribute(key, obj, ServletRequestAttributes.SCOPE_SESSION);
    }
    
    public static void setCookie(HttpServletResponse res, String Key, String value, int SecondLimit){
        Cookie cookie;
		try {
			cookie = new Cookie(Key, URLEncoder.encode(value, "utf-8"));
			if(SecondLimit <= 0) SecondLimit = 60 * 60 * 24; //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
	        cookie.setMaxAge(SecondLimit); 
	        cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
	        res.addCookie(cookie); //res
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    public static String getCookie(HttpServletRequest req, String Key){
    	Cookie[] cookies=req.getCookies(); // 모든 쿠키 가져오기
    	if(cookies!=null){
    		try {
		       for (Cookie c : cookies) {
		           String name = c.getName(); // 쿠키 이름 가져오기
		           String value = c.getValue(); // 쿠키 값 가져오기
		           if (name.equals(Key)) {
		               return URLDecoder.decode(value, "utf-8");
		           	}
		       	}
    		}catch(Exception e) { e.printStackTrace(); }
    	}
    	return null;
    }
}
