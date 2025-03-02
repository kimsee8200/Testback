package org.example.plain.common.handler;

import org.example.plain.common.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;


@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody> handlerOtherException(Exception e) {
        ResponseBody responseBody = new ResponseBody(e.getMessage(), HttpStatus.valueOf(400),null);
        return new ResponseEntity<>(responseBody, HttpStatus.valueOf(400));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ResponseBody responseBody = new ResponseBody(ex.getMessage(), (HttpStatus) statusCode, body);
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
