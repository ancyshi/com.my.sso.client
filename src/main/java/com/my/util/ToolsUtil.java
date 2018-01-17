package com.my.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;

public class ToolsUtil {
	
	/**

	 * 根据名字获取cookie

	 * @param request

	 * @param name cookie名字

	 * @return

	 */

	public static String getCookieValueByName(HttpServletRequest request,String name){

	    Map<String,Cookie> cookieMap = ReadCookieMap(request);

	    if(!cookieMap.isEmpty() && cookieMap.containsKey(name)){

	        String sessionId = cookieMap.get(name).getValue();

	        return sessionId;

	    }else{

	        return null;

	    }   

	}

	/**

	 * 将cookie封装到Map里面

	 * @param request

	 * @return

	 */

	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  

	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();

	    Cookie[] cookies = request.getCookies();

	    if(null!=cookies){

	        for(Cookie cookie : cookies){

	            cookieMap.put(cookie.getName(), cookie);

	        }

	    }

	    return cookieMap;

	}
	
	public static String addressAppend(String ip, String port, Map<String,Object> params) {
		
		StringBuffer address = new StringBuffer();
		address.append("http://").append(ip).append(":").append(port);
		
		if(!params.isEmpty()){
			address.append("?");
			for (Map.Entry<String,Object> entry : params.entrySet()) {  
				address.append(entry.getKey()).append("=").append(entry.getValue());
			}
			
		}
		
		return address.toString();
		
	}
	

}
