package org.example.plain.common.handler;

import org.example.plain.common.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<ResponseBody> handleException(Exception e) {
        ResponseBody responseBody = new ResponseBody(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ResponseBody> handleNullPointerException(NullPointerException e) {
        ResponseBody responseBody = new ResponseBody<>(e.getMessage(), HttpStatus.NOT_FOUND,null);
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ResponseBody> hanadleAuthenticationFailed(AuthenticationException e){
        ResponseBody responseBody = new ResponseBody(e.getMessage(), HttpStatus.UNAUTHORIZED,null);
        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<ResponseBody> handleHttpClientErrorException(HttpClientErrorException e){
        ResponseBody responseBody = new ResponseBody(e.getMessage(), (HttpStatus) e.getStatusCode(),null);
        return new ResponseEntity<>(responseBody, e.getStatusCode());
    }
}
