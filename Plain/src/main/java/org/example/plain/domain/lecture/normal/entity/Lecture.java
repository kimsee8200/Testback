package org.example.plain.domain.lecture.normal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.plain.common.entity.TimeStampEntity;
import org.example.plain.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecture")
public class Lecture extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String lectureId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User teacher;

    private Integer type;

    private String title;

    private String description;

    private Integer price;
}
