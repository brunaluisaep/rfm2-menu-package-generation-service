package com.rfm.packagegeneration.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionResponse {
    private Date   timestamp;
    private   String message;
    private   String details;
    
    public ExceptionResponse(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    
    public String getTimestamp() {
        return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format( timestamp);
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getDetails() {
        return details;
    }
}
