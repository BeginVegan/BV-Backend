package com.beginvegan.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;

import javax.servlet.*;
import javax.servlet.http.Cookie;
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

            // Check if JSESSIONID cookie is already present in the request
            boolean jSessionIdExists = false;
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    log.info(cookie.getName());
                    log.info(cookie.getValue());
                    if (cookie.getName().equals("JSESSIONID")) {
                        jSessionIdExists = true;

                        break;
                    }
                }
            }

            if (jSessionIdExists) {
                // Modify existing JSESSIONID cookie attributes
                setSamesite(httpServletResponse, "JSESSIONID", cookieValue, 3600);
            } else {
                // Add a new JSESSIONID cookie
                ResponseCookie cookie = ResponseCookie.from("JSESSIONID", cookieValue)
                        .path("/")
                        .sameSite("None")
                        .httpOnly(false)
                        .secure(true)
                        .maxAge(3600)
                        .build();

                httpServletResponse.addHeader("Set-Cookie", cookie.toString());
            }
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
                    name + "=" + value + "; SameSite=None; HttpOnly=false; Secure=true; Max-Age=" + maxAge + ";"
            );
            response.setHeader("Set-Cookie", updatedSetCookieHeader);
        } else {
            // Cookie doesn't exist, add a new one
            ResponseCookie cookie = ResponseCookie.from(name, value)
                    .path("/")
                    .sameSite("None")
                    .httpOnly(false)
                    .secure(true)
                    .maxAge(maxAge)
                    .build();

            response.addHeader("Set-Cookie", cookie.toString());
        }
    }

}
