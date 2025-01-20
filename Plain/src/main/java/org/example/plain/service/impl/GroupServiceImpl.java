package org.example.plain.service.impl;

import org.example.plain.dto.GroupDTO;
import org.example.plain.entity.Group;
import org.example.plain.repository.GroupRepository;
import org.example.plain.service.interfaces.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    // 생성
    public void createGroup(GroupDTO groupDTO) {
        String joinCode = createJoinCode();
        groupDTO.setJoinCode(joinCode);
        groupRepository.save(groupDTO.toEntity());
    }
    public static String createJoinCode() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        byte[] hash;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(uuid.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        StringBuilder hexString = new StringBuilder();
        int value = 0;
        final String text = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
        final char[] textArray = text.toCharArray();
        for (int i = 0; i < 32; i++) {
            if (i % 4 == 0) {
                hexString.append(textArray[value%textArray.length]);
            } else {
                value += (hash[i] & 0xff);
            }
        }
        System.out.println(hexString);
        return hexString.toString();
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
