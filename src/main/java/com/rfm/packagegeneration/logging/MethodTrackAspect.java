package com.rfm.packagegeneration.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.rfm.packagegeneration.logging.annotation.TrackedMethod;
import com.rfm.packagegeneration.utility.Pair;
import com.rfm.packagegeneration.utility.StringHelper;

@Aspect
@Component
public class MethodTrackAspect {
    Logger log = LogManager.getLogger( this.getClass() );
    
    @Pointcut( "execution(@com.rfm.packagegeneration.logging.annotation.TrackedMethod  * *(..))" )
    public void executionMethodTrack ( ) {
    
    }
    @Around("executionMethodTrack()")
    public Object logTrackedData ( ProceedingJoinPoint joinPoint ) throws Throwable {
        
        MethodSignature signature        = ( MethodSignature ) joinPoint.getSignature();
        TrackedMethod   methodTrack      = signature.getMethod().getAnnotation( TrackedMethod.class );
        Object          result           = null;
        
        if (methodTrack != null) {
            Pair<String, String> enterMethodPair = enterMethodInfo(joinPoint);
            String tag = enterMethodPair.getElement0();
            String msg = enterMethodPair.getElement1();
            
            log.info("Called: " + tag + "." + msg );
            
            result = joinPoint.proceed();
            Pair<String, String> exitMethodPair = exitMethodInfo( joinPoint, result);
            String               exitTag        = exitMethodPair.getElement0();
            String exitMsg = exitMethodPair.getElement1();
            
            log.info("Return: " + exitTag + "." + exitMsg);
        }
        return result;
    }
    
    private Pair< String, String > enterMethodInfo ( JoinPoint joinPoint ) {
        
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        
        Class<?> cls = codeSignature.getDeclaringType();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        
        StringBuilder builder = new StringBuilder();
        builder.append(methodName).append('(');
        for (int i = 0; i < parameterValues.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(parameterNames[i]).append('=');
            builder.append(StringHelper.toString(parameterValues[i]));
        }
        builder.append(')');
        
        String className = asTag(cls);
        
        return new Pair<>(className, builder.toString());
        
    }
    
    private static Pair< String, String > exitMethodInfo ( JoinPoint joinPoint, Object result ) {
        
        Signature signature = joinPoint.getSignature();
        
        Class<?> cls = signature.getDeclaringType();
        String methodName = signature.getName();
        boolean hasReturnType = signature instanceof MethodSignature && ((MethodSignature) signature).getReturnType() != void.class;
        
        StringBuilder builder = new StringBuilder(methodName);
        
        if (hasReturnType) {
            builder.append(" = ");
            builder.append( StringHelper.toString( result));
        }
        
        return new Pair<>(asTag(cls), builder.toString());
    }
    
    private static String getFullName ( Class< ? > cls ) {
        
        if ( cls.isAnonymousClass() ) {
            return asTag( cls.getEnclosingClass() );
        }
        return cls.getName();
    }
    
    private static String asTag ( Class< ? > cls ) {
        
        if ( cls.isAnonymousClass() ) {
            return asTag( cls.getEnclosingClass() );
        }
        return cls.getSimpleName();
    }
    
   
}
