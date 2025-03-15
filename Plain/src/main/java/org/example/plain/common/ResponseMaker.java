package org.example.plain.common;

import org.example.plain.common.enums.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMaker<T> {

    public static ResponseEntity<ResponseField> noContent(){
        ResponseField responseField = new ResponseField<>(Message.OK.name(), HttpStatus.NO_CONTENT,null);
        return new ResponseEntity<>(responseField, responseField.getStatus());
    }

    public ResponseEntity<ResponseField<T>> ok(T data){
        ResponseField<T> responseField = new ResponseField<>(Message.OK.name(), HttpStatus.OK,data);
        return new ResponseEntity<>(responseField, responseField.getStatus());
    }

    public static ResponseEntity<ResponseField> internalServerError(String message){
        ResponseField responseField = new ResponseField<>(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        return new ResponseEntity<>(responseField, responseField.getStatus());
    }

}
