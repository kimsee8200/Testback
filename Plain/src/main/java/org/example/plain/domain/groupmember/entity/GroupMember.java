package org.example.plain.domain.groupmember.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.domain.group.entity.Group;
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
    private Group group;

    @MapsId("user")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public GroupMember(Group group, User user) {
        this.id = new GroupMemberId(group.getGroupId(), user.getId());
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

