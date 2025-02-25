package org.example.plain.domain.groupmember.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.common.enums.Role;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.groupmember.dto.GroupMemberDTO;
import org.example.plain.domain.user.dto.UserRequestResponse;
import org.example.plain.domain.user.entity.User;

import java.io.Serializable;

@Getter
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


    public GroupMember(ClassLecture group, User user) {
        this.id = new GroupMemberId(group.getId(), user.getId());
        this.group = group;
        this.user = user;
    }

    public GroupMemberDTO toDTO() {
        return GroupMemberDTO.builder()
                .group(group)
                .userRequestResponse(new UserRequestResponse(user))
                .build();
    }
}

