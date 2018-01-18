package com.my.util;

import java.util.HashMap;
import java.util.Map;

public class ToolsUtilTest {

	public static void main(String[] args) {
		String a = "adfe";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("a", a);
		String result = ToolsUtil.addressAppend("localhost","8080","/fe/fe",params);
		System.out.println(result);
		
	}

}
