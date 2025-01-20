package org.example.plain.domain.homework.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class WorkSubmitFieldId implements Serializable {
    String user;
    String work;

    public WorkSubmitFieldId(String user, String work) {
        this.user = user;
        this.work = work;
    }
}
