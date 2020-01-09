package com.podo.pododev.web.global.aop;

import com.podo.pododev.web.domain.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@RequiredArgsConstructor
@Component
public class AllArgsNotNullChecker {

    @Before("@annotation(com.podo.pododev.web.global.aop.AllArgsNotNull)")
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
