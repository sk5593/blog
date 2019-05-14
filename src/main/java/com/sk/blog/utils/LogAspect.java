//package com.sk.blog.utils;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
///**
// * @Aspect 当前类是一个切面类
// */
//@Aspect
//@Component
//public class LogAspect {
//    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
//
//    //抽取公共的切入点表达式
//    @Pointcut("execution(public * com.sk.blog.controller..*.*(..))")
//    public void webLog() {
//    }
//    //在目标方法之前运行
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) {
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        // 记录下请求内容
//        LOGGER.info("URL : " + request.getRequestURL().toString() + ",IP : " + request.getRemoteAddr() + ",CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + ",ARGS : " + Arrays.toString(joinPoint.getArgs()));
//    }
//    //方法正常返回
//    @AfterReturning(returning = "object", value = "webLog()")
//    public void doAfterReturning(Object object) {
//        // 处理完请求，返回内容
//        LOGGER.info("RESPONSE : " + object);
//    }
//    @AfterThrowing(value = "webLog()",throwing = "exception")
////    joinPoint参数必须在第一位
//    public void doAfterThrowing(JoinPoint joinPoint,Exception exception)
//    {
//        LOGGER.error("THROWING："+exception.getMessage()+joinPoint.getSignature().getName());
//    }
//}
