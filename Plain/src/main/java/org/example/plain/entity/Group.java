package org.example.plain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.plain.dto.GroupDTO;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"group\"")
public class Group {
    @Id
    @Column(name = "g_id", length = 100, nullable = false)
    private String groupId;

    @Column(name = "g_name", columnDefinition = "tinytext", nullable = false)
    private String groupName;

    @Column(name = "g_code", length = 10, nullable = false)
    private String joinCode;

    public GroupDTO toDTO() {
        return GroupDTO.builder()
                .groupId(groupId)
                .groupName(groupName)
                .joinCode(joinCode)
                .build();
    }
}
