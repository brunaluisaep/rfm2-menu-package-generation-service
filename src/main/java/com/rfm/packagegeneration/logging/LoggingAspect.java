package com.rfm.packagegeneration.logging;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LogManager.getLogger("LoggingAspect");


    @AfterThrowing(value ="(execution(* com.rfm.packagegeneration.service.*.*(..)))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {

        // Get Stack Trace
        // --------------------------------------------------------------------------
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));

        // Get Exception Details
        // --------------------------------------------------------------------------
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String tclass = signature.getDeclaringTypeName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


        log.error("EXCEPTION =======================================================================");
        log.info("Class         : {}", tclass);
        log.info("TimeStamp     : {}", timeStamp);
        log.info("Method        : {}", methodName);
        log.info("Arguments     : {}", arguments);
        log.info("Exception Type: {}", ex.getClass().getName());
        log.info("Exception Msg : {}", ex.getMessage());
        log.error("StackTrace    : ", ex);
       
        log.info("=================================================================================");
    }
}
