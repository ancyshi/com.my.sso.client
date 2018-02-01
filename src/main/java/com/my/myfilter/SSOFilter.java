package com.my.myfilter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.cache.CookieCache;
import com.my.cache.LocalSessionCache;
import com.my.factory.AbstractFactory;
import com.my.factory.LocalSession;
import com.my.factory.SessionFactory;
import com.my.model.CookieId;
import com.my.util.CheckExceptionUtil;
import com.my.util.MyHttpUtils;
import com.my.util.SecurityUtils;
import com.my.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Order(1)
//重点
@WebFilter(filterName = "ssoFilter", urlPatterns = "/*")
public class SSOFilter  extends HttpServlet  implements Filter {

    @Resource
    private CookieCache cookieCache;

    @Autowired
    private Environment env;

    @Resource
    private LocalSessionCache localSessionCache;

    // sso server服务器地址
    @Value(value = "${ssoServerUrl:127.0.0.1:8077}")
    private String ssoServerUrl ;

    private  final  String tokenVerifyUri ="/server/auth/verify";

    private final String pageLoginUri ="/server/page/login";

    private  final String HTTPStr = "http://";

    private final String HTTPSStr = "https://";

    private AbstractFactory abstractFactory = new SessionFactory();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        CheckExceptionUtil.checkString(ssoServerUrl, "sso server host is empty!");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
            //  验证是否有token，且有效
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String tokenStr = (String) request.getParameter("token");
        request.setAttribute("returnURL" ,request.getRequestURI());
            if (tokenStr != null ) {
                JSONObject tokenInfoObj = this.verify(tokenStr,request,response);
                // 生成本地会话和全局会话，并且放到cookie中
                if(!tokenInfoObj.getString("verifyResult").isEmpty() && tokenInfoObj.getString("verifyResult").equals("true")) {
                    addTokenToCookie(tokenInfoObj,request,response);
                    chain.doFilter(request, response);
                }
                return;
            }

            // token无效，查看是否存在局部会话,且有效
        String urlId =  SecurityUtils.getBase64(request.getAttribute("returnURL").toString());
            String localSessionId = ToolsUtil.getCookieValueByName(request, urlId);
            if (localSessionId != null && this.verifyLocalSession( localSessionId)) {
                chain.doFilter(request, (HttpServletResponse) response);
                return;
            }

            // 不存在就重定向到服务器端认证全局会话
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("returnURL", request.getRequestURI());
            String redirectURL = ToolsUtil.addressAppend(HTTPStr, ssoServerUrl, pageLoginUri, map);
            response.sendRedirect(redirectURL);
    }

    /**
     * 如果token有效，那么就要创建本地会话，保存到cookie中
     * @param tokenInfoObj
     * @param httpRequest
     * @param response
     */
    private void
    addTokenToCookie(JSONObject tokenInfoObj, HttpServletRequest httpRequest, HttpServletResponse response) {
//        HttpSession session = httpRequest.getSession(true);
        String urlCodeString =SecurityUtils.getBase64(httpRequest.getAttribute("returnURL").toString());

        // 工厂方法模式，生产一个localSession
        LocalSession localSession = (LocalSession) abstractFactory.generateSession();
        localSession.setSessionIdStr(urlCodeString);
        localSession.setApplicationName(httpRequest.getParameter("returnURL"));
        localSession.setUserName(tokenInfoObj.getString("userName"));
        localSession.setPassWord(tokenInfoObj.getString("passWord"));


        CookieId localCookieId = new CookieId();
        localCookieId.setCookiesId(localSession.getSessionIdStr());
        localCookieId.setGlobalId( tokenInfoObj.getString("globalSessionId"));
        try {
                cookieCache.save(localCookieId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 采用cookie的方式记录下两个sessionId
        Cookie localSessionCookie = new Cookie(urlCodeString, localSession.getSessionIdStr());
        localSessionCookie.setPath("/");
        localSessionCookie.setMaxAge(-1);
        Cookie globalSessionCookie = new Cookie("globalSessionId", tokenInfoObj.getString("globalSessionId"));
        globalSessionCookie.setPath("/");
        globalSessionCookie.setMaxAge(-1);
        response.addCookie(globalSessionCookie);
        response.addCookie(localSessionCookie);
    }

    /**
     * 验证局部会话是否有效
     * @param localSessionId
     * @return
     */
    private boolean verifyLocalSession(String localSessionId) {
//       String result =  cookieCache.jedisGet(urlId);
    CookieId cookieId =    cookieCache.getCookieId(localSessionId);
       if (cookieId != null ) {
            return true;
       }
        return false;
    }

    /**
     * 验证token是否有效
     * @param tokenStr token的id
     * @param httpRequest
     * @param response
     * @return  true有效
     */
    private JSONObject verify(String tokenStr, HttpServletRequest httpRequest, HttpServletResponse response) {
        // 向认证中心发送验证token请求
        // 去server端的接口/server/auth/verify验证token的有效性
//        String verifyURL = "http://" + env.getProperty("sso.server") + env.getProperty("sso.server.verify");
        String verifyURL = "http://" + ssoServerUrl+ tokenVerifyUri;
        // serverName作为本应用标识
        MyHttpUtils myHttpUtils = new MyHttpUtils();
        JSONObject reqObj = new JSONObject();
        reqObj.put("token", tokenStr);
        JSONObject tokenInfo = new JSONObject();
        try {
            String tokenInfoStr;
            tokenInfoStr = myHttpUtils.httpPostJsonObj(verifyURL, reqObj, "utf-8");
            tokenInfo = JSON.parseObject(tokenInfoStr);
        } catch (IOException e) {
           return tokenInfo;
        }
        return tokenInfo;

    }
}
