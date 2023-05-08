package com.shop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // PointCut : 적용할 지점 또는 범위 선택
    @Pointcut("execution(public * com.shop.service..*(..))")
    private void publicTarget() { }

    // Advice : 실제 부가기능 구현부
    @Around("publicTarget()")
    public Object calcPerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("start!!!!!");
        StopWatch sw = new StopWatch();
        sw.start();


        Object result = pjp.proceed();

        sw.stop();
        logger.info(pjp.getSignature().getDeclaringTypeName());
        logger.info(pjp.getSignature().getName());
        logger.info("END");
        logger.info("SPEND TIME: {} ms", sw.getLastTaskTimeMillis());
        return result;
    }
}