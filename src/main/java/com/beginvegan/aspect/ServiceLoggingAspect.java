package com.beginvegan.aspect;

import com.beginvegan.util.LogFileAppender;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Generated
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

        try {
            params.put("class", className);
            params.put("method", methodName);
            params.put("params", args);
            params.put("log_time", LocalDateTime.now());
        } catch (Exception e) {
            log.error("ServiceLoggingAspect error", e);
        }

        String logMessage = String.format(
                "\n" +
                "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■" +
                "      <<Service Log>>\n" +
                "      ▶ Service: %s\t\t▶ Method: %s\n" +
                "      ▶ Params: %s\t\t▶ Log Time: %s\n" +
                "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■",
                params.get("class"),
                params.get("method"),
                params.get("params"),
                params.get("log_time")
        );

        log.info(logMessage);
        LogFileAppender.appendLog(logMessage);

        Object result = joinPoint.proceed();
        return result;
    }
}