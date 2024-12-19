package com.sivalabs.blog.config.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* org.springframework.data.repository.CrudRepository+.*(..))")
    void springDataRepoMethods() {
    }

    @Pointcut("execution(* com.sivalabs.blog..*(..))")
    void allPublicMethods() {
    }

    @Pointcut("@within(org.springframework.transaction.annotation.Transactional) || "
            + "@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {
        // pointcut definition
    }

    @Before("execution(* com.sivalabs.blog..*(..))")
    //@Before("allPublicMethods()")
    //@Before("execution(* com.sivalabs.blog..*(..)) || springDataRepoMethods()")
    //@Before("allPublicMethods() || springDataRepoMethods()")
    void logBefore(JoinPoint joinPoint) {
        //joinPoint.getArgs();
        //joinPoint.getTarget();
        log.info(
                "Enter: {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @Around("execution(* com.sivalabs.blog..*(..)) && transactionalMethods()")
    Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        var result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info(
                "Time taken for{}.{}() is {} ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                (end - start));
        return result;
    }
}
