package org.example.plain.domain.lecture.lectureMember.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.example.plain.domain.user.entity.User;

@Data
@Entity
@NoArgsConstructor
public class LectureMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private User userId;

    @ManyToOne
    private Lecture lectureId;


    public static LectureMember chaingeToRequestLectureMember(User user, Lecture lecture) {
        return new LectureMember(null,user,lecture);
    }

    public LectureMember(String id, User user, Lecture lecture) {
        this.id = id;
        this.userId = user;
        this.lectureId = lecture;
    }

}
