package org.example.plain.service.interfaces;

import org.example.plain.dto.GroupMemberDTO;
import org.example.plain.entity.Group;
import org.example.plain.entity.User;

import java.util.List;

public interface GroupMemberService {
    List<GroupMemberDTO> readGroupMemberAll(String groupId);
    GroupMemberDTO readGroupMember(String groupId, String userId);
    void joinGroup(String groupId, String userId);
    void quitGroup(String groupId, String userId);
}
