package org.example.plain.common.handler;

import org.example.plain.common.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;


@ControllerAdvice
public class ControllerExceptionHandler {

//    @ExceptionHandler({SQLException.class})
//    public ResponseEntity<ResponseBody> handleException(Exception e) {
//        return new ResponseEntity<>()
//    }


}
