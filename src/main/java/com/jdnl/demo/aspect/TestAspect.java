package com.jdnl.demo.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {
    private Logger logger = Logger.getLogger(getClass());

    @Pointcut("execution(* com.jdnl.demo.service.TestServiceImpl.add(..))")
    public void TestAspect() {
    }

    @Before("TestAspect()&&args(num1,num2)")
    void doBefore(JoinPoint joinPoint, int num1, int num2) throws Exception {
        System.out.println("@Before：目标方法为：" +
                joinPoint.getSignature().getDeclaringTypeName() +
                "." +
                joinPoint.getSignature().getName());

        joinPoint.getArgs();
        logger.info("计算之前" + num1);
    }

//    @Around("TestAspect(num1,num2)")
//    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object obj = joinPoint.proceed();
//        return obj;
//    }

//    @AfterReturning("TestAspect(num1,num2)")
//    void doAfterReturning(JoinPoint joinPoint) {
//        logger.info("计算之后");
//    }

    @AfterReturning(pointcut = "TestAspect()", returning = "num")
    void doAfterReturning(int num) {
        logger.info(num);
        logger.info("计算之后");

    }

}
