package org.example.plain.controller;

import org.example.plain.dto.GroupDTO;
import org.example.plain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/new-group")
    public void createGroup(@RequestBody GroupDTO groupDTO) {
        groupService.createGroup(groupDTO);
        System.out.println("그룹 생성");
    }

    @GetMapping("/")
    public List<GroupDTO> readGroup() {
        List<GroupDTO> groupList = groupService.readGroupAll();
        return groupList;
    }

    @GetMapping("/{organId}")
    public String readGroupById(String organId) { // OrganDTO
        return "그룹 하나";
    }

    @PatchMapping("/{organId}")
    public String updateGroupById(String organId) {
        return "그룹 업데이트";
    }

    @DeleteMapping("/{organId}")
    public String deleteGroupById(String organId) {
        return "그룹 삭제";
    }

    @GetMapping("/{organId}/members")
    public String readGroupMembers(String organId) {
        return "그룹 맴버";
    }

}
