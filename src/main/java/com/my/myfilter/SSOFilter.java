package com.my.myfilter;
import com.my.cache.CookieCache;
import com.my.util.ToolsUtil;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Order(1)
//重点
@WebFilter(filterName = "ssoFilter", urlPatterns = "/*")
public class SSOFilter  extends HttpServlet  implements Filter {

    @Resource
    private CookieCache cookieCache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String app1SessionId = ToolsUtil.getCookieValueByName((HttpServletRequest) request, "app1SessionId");

        // 如果localSeeionId不存在且token也不存在，就要去登录
        try {
            //  验证是否有token，且有效

            // token无效，查看是否存在局部会话

            // 存在，就doChain

            // 不存在就重定向到服务器端

            if (app1SessionId == null || !cookieCache.getCookie(app1SessionId).equals("true")) {
                // 重定向到认证中心
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("returnURL", "app1");
                String redirectURL = ToolsUtil.addressAppend("localhost", "8077", "/server/page/login", map);

                // 去验证是否有全局会话
                HttpServletResponse  response1 = (HttpServletResponse)response;
                response1 .sendRedirect(redirectURL);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
