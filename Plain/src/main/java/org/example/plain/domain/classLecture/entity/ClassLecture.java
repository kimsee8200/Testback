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
public class ClassLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
