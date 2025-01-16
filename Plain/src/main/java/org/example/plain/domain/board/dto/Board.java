package org.example.plain.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private String id;
    private String title;
    private String content;
}
