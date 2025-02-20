package org.example.plain.domain.groupmember.dto;

import lombok.Builder;
import lombok.Data;
import org.example.plain.domain.group.entity.Group;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.user.dto.UserRequestResponse;
import org.example.plain.domain.user.entity.User;

@Data
@Builder
public class GroupMemberDTO {
    private Group group;
    private UserRequestResponse userRequestResponse;

    public GroupMember toEntity() {
        GroupMemberId id = new GroupMemberId(group.getGroupId(), userRequestResponse.getId());
        return GroupMember.builder()
                .id(id)
                .group(group)
                .user(new User(userRequestResponse))
                .build();
    }
}
