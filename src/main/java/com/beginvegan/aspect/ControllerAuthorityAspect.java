package com.beginvegan.aspect;

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

@Slf4j
@Aspect

@Component
public class ControllerAuthorityAspect {
    @Pointcut("execution(* com.beginvegan.controller.MemberController.memberList(..))")
    public void memberListPointcut() {
    }

    @Pointcut("execution(* com.beginvegan.controller.ReservationController.reservationList(..))")
    public void reservationListPointcut() {
    }

    @Pointcut("execution(* com.beginvegan.controller.RestaurantController.restaurantAdd(..))")
    public void restaurantAddPointcut() {
    }

    @Pointcut("execution(* com.beginvegan.controller.RestaurantController.restaurantModify(..))")
    public void restaurantModifyPointcut() {
    }


    @Pointcut("execution(* com.beginvegan.controller.RestaurantController.restaurantRemove(..))")
    public void restaurantRemovePointcut() {
    }

    @Around("memberListPointcut() || reservationListPointcut() || restaurantAddPointcut() || restaurantModifyPointcut() || restaurantRemovePointcut()")
    public Object controllerAuthenticationCheck(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute("memberEmail") == null || !((String) session.getAttribute("memberRole")).equals("admin")) {
            throw new Exception("접근 권한이 없습니다");
        }

        Object result = joinPoint.proceed();
        return result;
    }

}