package org.example.plain.domain.groupmember.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.groupmember.dto.GroupMemberDTO;
import org.example.plain.domain.user.dto.UserRequest;
import org.example.plain.domain.user.entity.User;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"group_member\"")
public class GroupMember implements Serializable {
    @EmbeddedId
    private GroupMemberId id;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "g_id")
    private ClassLecture group;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String authority;

    public static GroupMember makeGroupMember(ClassLecture group, User user){
        return new GroupMember(user.getId(),group.getId(),group,user,"USER");
    }

    public static GroupMember makeGroupLeader(ClassLecture group, User user){
        return new GroupMember(user.getId(),group.getId(),group,user,"LEADER");
    }

    public GroupMember(String userId, String classId, ClassLecture classLecture, User user, String role ) {
        this.id = new GroupMemberId(userId, classId);
        this.group = classLecture;
        this.user = user;
        this.authority = role;
    }

    public GroupMemberDTO toDTO() {
        return GroupMemberDTO.builder()
                .group(group)
                .userRequest(new UserRequest(user))
                .build();
    }
}

