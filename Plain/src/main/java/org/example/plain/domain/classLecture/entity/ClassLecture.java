package org.example.plain.domain.classLecture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.plain.common.enums.ClassType;
import org.example.plain.domain.classLecture.dto.ClassAddRequest;
import org.example.plain.domain.classMember.entity.ClassMember;
import org.example.plain.domain.member.dto.Member;
import org.example.plain.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class_lecture")
public class ClassLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "c_id")
    private String id;

    @OneToMany(mappedBy = "classLecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassMember> members = new ArrayList<>();

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

    public void addMember(User user) {
        this.members.add(new ClassMember(this, user));
    }

    public void updateClass(ClassAddRequest classAddRequest) {
        this.title = classAddRequest.title();
        this.description = classAddRequest.description();
        this.classImg = classAddRequest.classImg();
        this.price = classAddRequest.price();
        this.classType = classAddRequest.classType();
        this.maxMember = classAddRequest.maxMember();
    }
}
