package com.podo.pododev.backend.global.aop.argschecker;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Component
@Aspect
public class AllArgsNotNullChecker {

    @Before(value = "@annotation(com.podo.pododev.backend.global.aop.argschecker.AllArgsNotNull)")
    public void check(JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        final Class clazz = joinPoint.getSignature().getDeclaringType();
        final String methodName = joinPoint.getSignature().getName();

        for (Object arg : args) {
            if(Objects.isNull(arg)){
                throw new NotAllArgsNullException(clazz.getName() + "." + methodName + " ( "  + Arrays.toString(args) + " )");
            }
        }
    }


}
