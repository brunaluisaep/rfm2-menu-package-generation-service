package com.rfm.packagegeneration.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rfm.packagegeneration.logging.LogLevel;

@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
public @interface TrackedMethod {
    LogLevel level() default LogLevel.INFO;
    boolean traceSpendTime() default true;
}
