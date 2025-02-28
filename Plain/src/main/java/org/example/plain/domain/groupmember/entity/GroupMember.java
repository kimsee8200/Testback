package org.example.plain.domain.groupmember.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private ClassLecture classLecture;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public GroupMemberDTO toDTO() {
        return GroupMemberDTO.builder()
                .group(getClassLecture())
                .userRequestResponse(new UserRequestResponse(user))
                .build();
    }
}

