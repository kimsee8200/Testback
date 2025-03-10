package org.example.plain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseBody<T> {
    private String message;
    private HttpStatus status;
    private T body;
}
