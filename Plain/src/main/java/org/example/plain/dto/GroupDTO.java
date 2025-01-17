package org.example.plain.dto;

import lombok.*;
import org.example.plain.entity.Group;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {
    private String groupId;
    private String groupName;

    public Group toEntity() {
        return Group.builder()
                .groupId(groupId)
                .groupName(groupName)
                .build();
    }
}
