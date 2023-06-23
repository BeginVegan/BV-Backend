package com.beginvegan.aspect;

import com.beginvegan.util.LogFileAppender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect

@Component
public class ServiceLoggingAspect {
    @Pointcut("execution(* com.beginvegan.service.*.*(..))")
    public void servicePointcut() {
    }

    @Around("servicePointcut()")
    public Object serviceLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String memberEmail = session.getAttribute("memberEmail") != null ? (String) session.getAttribute("memberEmail") : "";

        try {
            params.put("class", className);
            params.put("method", methodName);
            params.put("params", args);
            params.put("log_time", LocalDateTime.now());
            params.put("member_email", memberEmail);

        } catch (Exception e) {
            log.error("ServiceLoggingAspect error", e);
        }

        String logMessage = String.format(
                "\n" +
                        "◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆" +
                        "      <<Service Log>>\n" +
                        "      ▶ Service: %s\t\t\t▶ Method: %s\n" +
                        "      ▶ Params: %s\t\t\t▶ Log Time: %s\n" +
                        "      ▶ MemberEmail: %s\n" +
                        "◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆",
                params.get("class"),
                params.get("method"),
                params.get("params"),
                params.get("log_time"),
                params.get("member_email")
        );

        log.info(logMessage);
        LogFileAppender.appendLog(logMessage);

        Object result = joinPoint.proceed();
        return result;
    }
}