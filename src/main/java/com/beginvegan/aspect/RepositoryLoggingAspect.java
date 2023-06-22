package com.beginvegan.aspect;

import com.beginvegan.util.LogFileAppender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect

@Component
public class RepositoryLoggingAspect {

    @Pointcut("execution(* com.beginvegan.repository.*.*(..))")
    public void repositoryPointcut() {
    }

    @Around("repositoryPointcut()")
    public Object repositoryLogging(ProceedingJoinPoint joinPoint) throws Throwable {

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
            log.error("RepositoryLoggingAspect error", e);
        }

        String logMessage = String.format(
                "\n" +
                        "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■" +
                        "      <<Repository Log>>\n" +
                        "      ▶ Repository: %s\t\t▶ Method: %s\n" +
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