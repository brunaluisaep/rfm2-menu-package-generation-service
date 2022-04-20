package com.rfm.packagegeneration.utility;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import jodd.io.StreamUtil;

public class RequestWrapper extends HttpServletRequestWrapper {
    
    private final byte[] body;
    
    public RequestWrapper ( HttpServletRequest request ) throws IOException {
        
        super( request );
        body = StreamUtil.readBytes( request.getReader(), "UTF-8" );
        
    }
    
    @Override
    public BufferedReader getReader ( ) throws IOException {
        
        return new BufferedReader( new InputStreamReader( getInputStream() ) );
    }
    
    @Override
    public ServletInputStream getInputStream ( ) throws IOException {
        
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream( body );
        return new ServletInputStream() {
            
            @Override
            public int read ( ) throws IOException {
                
                return byteArrayInputStream.read();
            }
            
            @Override
            public boolean isFinished ( ) {
                
                return false;
            }
            
            @Override
            public boolean isReady ( ) {
                
                return false;
            }
            
            @Override
            public void setReadListener ( ReadListener arg0 ) {
            
            }
        };
    }
    
}
