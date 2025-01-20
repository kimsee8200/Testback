package Canlender;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Calender {
    private String c_id;
    private String u_id;
    private String date_info;
    private String title;
    private String content;
}
