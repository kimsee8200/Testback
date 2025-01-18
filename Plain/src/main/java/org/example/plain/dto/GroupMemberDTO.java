package org.example.plain.dto;

import lombok.Builder;
import lombok.Data;
import org.example.plain.entity.Group;
import org.example.plain.entity.GroupMember;
import org.example.plain.entity.GroupMemberId;
import org.example.plain.entity.User;

@Data
@Builder
public class GroupMemberDTO {
    private Group group;
    private User user;

    public GroupMember toEntity() {
        GroupMemberId id = new GroupMemberId(group.getGroupId(), user.getUserId());
        return GroupMember.builder()
                .id(id)
                .group(group)
                .user(user)
                .build();
    }
}
