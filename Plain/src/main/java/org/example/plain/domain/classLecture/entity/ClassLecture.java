package org.example.plain.domain.classLecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.plain.common.enums.ClassType;
import org.example.plain.domain.user.entity.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class")
public class ClassLecture {

    @Id
    @Column(name = "c_id")
    private String id;

    private String title;

    private String description;

    private String code;

    private String classImg;

    private Long price;

    @Enumerated(EnumType.STRING)
    private ClassType classType;

    private Long maxMember;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User instructor;

    public void updateClass(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
