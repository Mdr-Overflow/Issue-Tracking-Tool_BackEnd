package Issue.Tracking.Tool.LoginSessionPoint.filter;

import Issue.Tracking.Tool.LoginSessionPoint.securityConfig.MutableHttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CheckAuthCookieFilter implements Filter {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(httpServletRequest);

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                log.debug(cookie.getName() + " : " + cookie.getValue());
                if (cookie.getName().equalsIgnoreCase("auth")) {
                    mutableRequest.putHeader("Authorization", URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));
                }
            }
        }

        chain.doFilter(mutableRequest, response);

    }

}
