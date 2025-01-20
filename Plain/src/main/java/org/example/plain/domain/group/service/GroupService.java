package org.example.plain.domain.group.service;

import org.example.plain.domain.group.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    void createGroup(GroupDTO groupDTO);
    List<GroupDTO> readGroupAll();
    GroupDTO readGroup(String groupId);
    void updateGroup(GroupDTO groupDTO);
    void deleteGroup(String groupId);
}
