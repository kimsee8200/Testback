package org.example.plain.domain.group.controller;

import org.example.plain.domain.group.dto.GroupDTO;
import org.example.plain.domain.group.service.GroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @PostMapping("/new-group")
    public String createGroup(@RequestBody GroupDTO groupDTO) {
        groupServiceImpl.createGroup(groupDTO);
        return "그룹 생성";
    }

    @GetMapping("/")
    public List<GroupDTO> readGroup() {
        List<GroupDTO> groupList = groupServiceImpl.readGroupAll();
        return groupList;
    }

    @GetMapping("/{groupId}")
    public GroupDTO readGroupById(String groupId) {
        return groupServiceImpl.readGroup(groupId);
    }

    @PatchMapping("/{groupId}")
    public String updateGroupById(@RequestBody GroupDTO groupDTO) {
        groupServiceImpl.updateGroup(groupDTO);
        return "그룹 업데이트";
    }

    @DeleteMapping("/{groupId}")
    public String deleteGroupById(String groupId) {
        groupServiceImpl.deleteGroup(groupId);
        return "그룹 삭제";
    }
}
