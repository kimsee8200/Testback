package org.example.plain.dto;

import lombok.*;
import org.example.plain.entity.Group;

@Data
@Builder
public class GroupDTO {
    private String groupId;
    private String groupName;
    private String joinCode;

    public Group toEntity() {
        return Group.builder()
                .groupId(groupId)
                .groupName(groupName)
                .joinCode(joinCode)
                .build();
    }
}
