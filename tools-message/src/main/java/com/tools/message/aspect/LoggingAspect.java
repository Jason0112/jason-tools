package com.tools.message.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description :
 */
@Order(2)
@Aspect
@Component
public class LoggingAspect {


    @Pointcut("execution(* com.tools.message.service.impl.*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("before The method " + methodName + " beging  with " + JSONObject.toJSONString(joinPoint.getArgs()));
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("after The method " + methodName + " end ");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(" AfterReturning The method " + methodName + " end  with " + result);

    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("AfterThrowing The method " + methodName + " end  with " + ex);

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(" Around The method " + methodName + " beging  with " + JSONObject.toJSONString(joinPoint.getArgs()));
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("Around The method " + methodName + " end  with ");

        return result;
    }

}
