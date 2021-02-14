package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        long startTime = System.currentTimeMillis();

        System.out.println("Start : " + proceedingJoinPoint.toString());

        try
        {
            return proceedingJoinPoint.proceed();
        }
        finally {
            long endTime = System.currentTimeMillis();
            long totalExecuteTime = endTime - startTime;
            System.out.println("End : " + proceedingJoinPoint.toString() + " " + totalExecuteTime + "ms");
        }
    }
}
