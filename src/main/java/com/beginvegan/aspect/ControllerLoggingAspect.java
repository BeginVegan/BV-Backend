package com.beginvegan.aspect;

import com.beginvegan.util.LogFileAppender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect

@Component
public class ControllerLoggingAspect {
    @Pointcut("execution(* com.beginvegan.controller.*.*(..))")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()")
    public Object controllerLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String memberEmail = session.getAttribute("memberEmail") != null ? (String) session.getAttribute("memberEmail") : "";

        String controllerName = joinPoint.getSignature().getDeclaringType().getName();
        String methodName = joinPoint.getSignature().getName();
        Map<String, Object> params = new HashMap<>();

        try {
            params.put("controller", controllerName);
            params.put("method", methodName);
            params.put("params", getParams(request));
            params.put("log_time", LocalDateTime.now());
            params.put("request_uri", request.getRequestURI());
            params.put("http_method", request.getMethod());
            params.put("member_email", memberEmail);
        } catch (Exception e) {
            log.error("LoggerAspect error", e);
        }

        String logMessage = String.format(
                "\n" +
                        "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■" +
                        "      <<Controller Log>>\n" +
                        "      ▶ Controller: %s\t\t\t▶ Method: %s\n" +
                        "      ▶ Params: %s\t\t\t▶ Log Time: %s\t\t\n" +
                        "      ▶ MemberEmail: %s\n" +
                        "      ▶ Request URI: %s\t\t\t▶ HTTP Method: %s\n" +
                        "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■",
                params.get("controller"),
                params.get("method"),
                params.get("params"),
                params.get("log_time"),
                params.get("member_email"),
                params.get("request_uri"),
                params.get("http_method")
        );

        log.info(logMessage);
        LogFileAppender.appendLog(logMessage);

        Object result = joinPoint.proceed();
        return result;

    }

    private static JSONObject getParams(HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }
}