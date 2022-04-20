package com.rfm.packagegeneration.logging;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.rfm.packagegeneration.utility.RequestWrapper;
import com.rfm.packagegeneration.utility.ResponseWrapper;

@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {
    private static final Logger log = LogManager.getLogger( "RequestResponseLoggingFilter");
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("Initializing filter :{}", this);
    }
    
    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        RequestWrapper  wrappedRequest  = new RequestWrapper( ( HttpServletRequest ) request);
        ResponseWrapper wrappedResponse = new ResponseWrapper(( HttpServletResponse ) response);
        logRequest(wrappedRequest);
        chain.doFilter(wrappedRequest, wrappedResponse);
        wrappedResponse.flushBuffer();
        logResponse(wrappedResponse);
        
    }
    
    private void logRequest(RequestWrapper request) throws IOException {
        log.debug("REQUEST BEGIN =======================================================================");
        log.debug("URI         : {}", request.getRequestURI());
        log.debug("Method      : {}", request.getMethod());
        log.debug("Request body: {}", request.getReader().lines().collect( Collectors.joining()));
        log.debug("Parameters  : {}", getParams(request.getParameterMap()));
        log.debug("REQUEST END =========================================================================");
    }
    
    private void logResponse(ResponseWrapper response) throws IOException {
        byte[] res = response.getCopy();
        log.debug("RESPONSE BEGIN ======================================================================");
        log.debug("Status code  : {}", response.getStatus());
        log.debug("Response body: {}", new String(res, response.getCharacterEncoding()));
        log.debug("RESPONSE END ========================================================================");
    }
    
    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }
    
    public String getParams( Map<String, String[]> paramsMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry: paramsMap.entrySet()) {
            sb.append("\"" + entry.getKey() + "\" : \"" + String.join(",",entry.getValue()) + "\", ");
        }
        return sb.toString();
    }
}
