package org.example.plain.service.interfaces;

import org.example.plain.dto.GroupMemberDTO;
import org.example.plain.entity.Group;
import org.example.plain.entity.User;

import java.util.List;

public interface GroupMemberService {
    List<GroupMemberDTO> readGroupMemberAll(Group group);
    GroupMemberDTO readGroupMember(Group group, User user);
    void joinGroup(Group group, User user);
    void quitGroup(Group group, User user);
}
