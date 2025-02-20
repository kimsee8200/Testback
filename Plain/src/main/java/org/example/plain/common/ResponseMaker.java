package org.example.plain.common;

import org.example.plain.common.enums.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMaker<T> {

    public static ResponseEntity<ResponseBody> noContent(){
        ResponseBody responseBody = new ResponseBody<>(Message.OK.name(), HttpStatus.NO_CONTENT,null);
        return new ResponseEntity<>(responseBody, responseBody.getStatus());
    }

    public ResponseEntity<ResponseBody<T>> ok(T data){
        ResponseBody<T> responseBody = new ResponseBody<>(Message.OK.name(), HttpStatus.OK,data);
        return new ResponseEntity<>(responseBody, responseBody.getStatus());
    }

    public static ResponseEntity<ResponseBody> internalServerError(String message){
        ResponseBody responseBody = new ResponseBody<>(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        return new ResponseEntity<>(responseBody, responseBody.getStatus());
    }

}
