package org.example.plain.domain.groupmember.dto;

import lombok.Builder;
import lombok.Data;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.user.dto.UserRequestResponse;
import org.example.plain.domain.user.entity.User;

@Data
@Builder
public class GroupMemberDTO {
    private ClassLecture group;
    private UserRequestResponse userRequestResponse;

    public GroupMember toEntity() {
        GroupMemberId id = new GroupMemberId(group.getId(), userRequestResponse.getId());
        return GroupMember.builder()
                .id(id)
                .classLecture(group)
                .user(new User(userRequestResponse))
                .build();
    }
}