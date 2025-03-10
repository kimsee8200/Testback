package org.example.plain.domain.classLecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.plain.domain.user.entity.User;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groupclass")
public class ClassLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "g_id")
    private String id;

    @Column(name = "g_name")
    private String title;

    @Column(name = "lecture_id", nullable = true)
    private String lecture;

    @Column(name = "description")
    private String description;

    @Column(name = "g_code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private User instructor;

    public void updateClass(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
