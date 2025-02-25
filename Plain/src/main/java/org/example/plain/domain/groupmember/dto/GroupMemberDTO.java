package org.example.plain.domain.groupmember.dto;

import lombok.Builder;
import lombok.Data;
import org.example.plain.domain.groupmember.entity.Group;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.user.dto.User;
import org.example.plain.domain.user.entity.UserEntity;

@Data
@Builder
public class GroupMemberDTO {
    private Group group;
    private User user;

    public GroupMember toEntity() {
        GroupMemberId id = new GroupMemberId(group.getGroupId(), user.getId());
        return GroupMember.builder()
                .id(id)
                .group(group)
                .user(new UserEntity(user))
                .build();
    }
}
