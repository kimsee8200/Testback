package org.example.plain.domain.groupmember.dto;

import lombok.Builder;
import lombok.Data;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.groupmember.entity.ClassMember;
import org.example.plain.domain.groupmember.entity.ClassMemberId;
import org.example.plain.domain.user.dto.UserRequestResponse;
import org.example.plain.domain.user.entity.User;

@Data
@Builder
public class GroupMemberDTO {
    private ClassLecture group;
    private UserRequestResponse userRequestResponse;

    public ClassMember toEntity() {
        ClassMemberId id = new ClassMemberId(group.getId(), userRequestResponse.getId());
        return ClassMember.builder()
                .id(id)
                .classLecture(group)
                .user(new User(userRequestResponse))
                .build();
    }
}
