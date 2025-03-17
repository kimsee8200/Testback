package org.example.plain.domain.classMember.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.user.entity.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class_member")
public class ClassMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "c_id", nullable = false)
    private ClassLecture classLecture;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ClassMember(ClassLecture classLecture, User user) {
        this.classLecture = classLecture;
        this.user = user;
    }
}

