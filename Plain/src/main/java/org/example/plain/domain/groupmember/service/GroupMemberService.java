package org.example.plain.domain.groupmember.service;

import org.example.plain.domain.groupmember.dto.GroupMemberDTO;

import java.util.List;

public interface GroupMemberService {
    List<GroupMemberDTO> readGroupMemberAll(String groupId);
    GroupMemberDTO readGroupMember(String groupId, String userId);
    void joinGroup(String groupId, String userId);
    void quitGroup(String groupId, String userId);
}
