package org.example.plain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseField<T> {
    private String message;
    private HttpStatus status;
    private T body;

    public ResponseField<T> returnOkResponseField(T body){
        return new ResponseField<T>("정상적으로 처리되었습니다.", HttpStatus.OK, body);
    }
}
