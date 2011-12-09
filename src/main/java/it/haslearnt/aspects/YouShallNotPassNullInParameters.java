/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import static org.springframework.util.Assert.notNull;

@Aspect
public class YouShallNotPassNullInParameters {
    @Before("it.haslearnt.aspects.Pointcuts.allPublicMethodsExceptSettersAndDtos()")
    public void throwExceptionIfAnyParameterIsNull(JoinPoint joinPoint) {
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            Object argument = joinPoint.getArgs()[i];
            notNull(argument, "Argument at index " + i + " (of method called at this joinpoint) cannot be null");
        }
    }
}
