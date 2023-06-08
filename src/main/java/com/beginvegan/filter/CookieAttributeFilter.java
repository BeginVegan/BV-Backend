package com.beginvegan.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CookieAttributeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String cookieValue = httpRequest.getSession().getId(); // Retrieve the session ID or any other desired value

        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            setSamesite(httpServletResponse, "JSESSIONID", cookieValue, 3600);
        }

        chain.doFilter(request, response);
        log.info("CookieAttributeFilter");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private static void setSamesite(HttpServletResponse response, String name, String value, int maxAge) {
        String existingSetCookieHeader = response.getHeader("Set-Cookie");
        if (existingSetCookieHeader != null && existingSetCookieHeader.contains(name)) {
            // Cookie with the same name already exists, modify its attributes instead of adding a new one
            String updatedSetCookieHeader = existingSetCookieHeader.replaceFirst(
                    name + "=.*?;",
                    name + "=" + value + "; Domain=kro.kr; SameSite=None; HttpOnly=false; Secure=true; Max-Age=" + maxAge + ";"
            );
            response.setHeader("Set-Cookie", updatedSetCookieHeader);
        } else {
            // Cookie doesn't exist, add a new one
            ResponseCookie cookie = ResponseCookie.from(name, value)
                    .path("/")
                    .domain("kro.kr")
                    .sameSite("None")
                    .httpOnly(false)
                    .secure(true)
                    .maxAge(maxAge)
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
        }
    }


}
