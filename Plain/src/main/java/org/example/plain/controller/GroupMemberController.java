package org.example.plain.controller;

import org.example.plain.dto.GroupMemberDTO;
import org.example.plain.entity.Group;
import org.example.plain.service.impl.GroupMemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupMemberController {
    @Autowired
    private GroupMemberServiceImpl groupMemberServiceImpl;

    @GetMapping("/{groupId}/members")
    public List<GroupMemberDTO> readGroupMemberAll(String groupId) {
        return groupMemberServiceImpl.readGroupMemberAll(groupId);
    }
}
