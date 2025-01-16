package org.example.plain.domain.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.plain.domain.user.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkMember {
    private User user;
    private Work work;
    private boolean isSubmit;
    private boolean isLate;

}
