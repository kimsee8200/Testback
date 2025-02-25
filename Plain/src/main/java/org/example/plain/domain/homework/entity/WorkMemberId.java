package org.example.plain.domain.homework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class WorkMemberId implements Serializable {

    @Column(name = "h_id")
    private String work;
    @Column(name = "user_id")
    private String memberUser;

    public WorkMemberId(String work, String user) {
        this.work = work;
        this.memberUser = user;
    }
}
