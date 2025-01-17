package org.example.plain.service;

import org.example.plain.dto.GroupDTO;
import org.example.plain.entity.Group;
import org.example.plain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    // 생성
    public void createGroup(GroupDTO groupDTO) {
        groupRepository.save(groupDTO.toEntity());
    }

    // 전체 조회 -> 정렬 기준 확인
    public List<GroupDTO> readGroupAll() {
        List<Group> groupList = groupRepository.findAllByOrderByGroupNameAsc();
        List<GroupDTO> groupDTOList = new ArrayList<>();
        for (Group group : groupList) {
            groupDTOList.add(group.toDTO());
        }
        return groupDTOList;
    }
    // 개별 조회
    public GroupDTO readGroup(String groupId) {
        Optional<Group> organ = groupRepository.findById(groupId);
        return organ.map(Group::toDTO).orElse(null);
    }

    // 수정
    public void updateGroup(GroupDTO groupDTO) {
        groupRepository.save(groupDTO.toEntity());
    }

    // 삭제
    public void deleteGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }
}
