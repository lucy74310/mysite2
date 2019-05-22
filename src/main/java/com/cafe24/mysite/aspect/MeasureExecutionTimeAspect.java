package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

//실행시간측정 어스펙트
@Component
@Aspect
public class MeasureExecutionTimeAspect {
	
	//포인트컷 지정
	@Around("execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..))" )
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		
		// before
		StopWatch sw = new StopWatch();
		sw.start();
		
		
		
		// method 실행
		Object result = pjp.proceed();
		
		
		// after
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		
		
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName(); // signature -method
		String taskName = className + "." + methodName; 
		
		
		System.out.println("[Execution Time]["+ taskName+"] : " + totalTime);
		
		return result;
		
		
	}
}
