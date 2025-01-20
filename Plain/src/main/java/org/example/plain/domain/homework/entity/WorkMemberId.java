package org.example.plain.domain.homework.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class WorkMemberId implements Serializable {
    private String work;
    private String user;

    public WorkMemberId(String work, String user) {
        this.work = work;
        this.user = user;
    }
}
