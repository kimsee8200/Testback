package org.example.plain.organ;

import org.example.plain.dto.GroupDTO;
import org.example.plain.entity.Group;
import org.example.plain.repository.GroupRepository;
import org.example.plain.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;

    private Group group1;
    private Group group2;
    private GroupDTO groupDTO1;
    private GroupDTO groupDTO2;

    @BeforeEach
    public void setup() {
        group1 = Group.builder().groupId(UUID.randomUUID().toString()).groupName("대마고").build();
        group2 = Group.builder().groupId(UUID.randomUUID().toString()).groupName("플레인").build();
        groupDTO1 = GroupDTO.builder().groupId(UUID.randomUUID().toString()).groupName("용암중").build();
        groupDTO2 = GroupDTO.builder().groupId(UUID.randomUUID().toString()).groupName("상당초").build();
    }

    @Test
    public void createGroup() {
        // given
        groupRepository.deleteAll();

        // when
        groupService.createGroup(groupDTO1);
        groupService.createGroup(groupDTO2);

        // then
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList.size()).isEqualTo(2);
    }

    @Test
    public void readGroupAll() {
        // given
        groupRepository.deleteAll();
        groupRepository.save(group1);
        groupRepository.save(group2);

        // when
        List<GroupDTO> organList = groupService.readGroupAll();

        // then
        assertThat(organList.size()).isEqualTo(2);
        assertThat(organList.get(0).getGroupName()).isEqualTo(group1.getGroupName());
        assertThat(organList.get(1).getGroupName()).isEqualTo(group2.getGroupName());

    }
    // 개별 조회, 수정, 삭제
    // 그룹 내 맴버 조회
    @Test
    public void readOrganById() {
        //given
        groupRepository.deleteAll();
        groupRepository.save(group1);
        groupRepository.save(group2);

        // when
        GroupDTO organ = groupService.readGroup(group1.getGroupId());

        // then
        assertThat(organ.getGroupId()).isEqualTo(group1.getGroupId());
        assertThat(organ.getGroupName()).isEqualTo(group1.getGroupName());
    }

    @Test
    public void updateOrgan() {
        // given
        groupRepository.deleteAll();
        groupRepository.save(group1);
        groupRepository.save(group2);

        // when
        GroupDTO groupDTO = GroupDTO.builder().groupId(group2.getGroupId()).groupName("직화육포").build();
        groupService.updateGroup(groupDTO);

        // then
        Optional<Group> updatedOrgan = groupRepository.findById(group2.getGroupId());
        updatedOrgan.ifPresent(organ -> assertThat(organ.getGroupName()).isEqualTo(groupDTO.getGroupName()));
    }

    @Test
    public void deleteOrgan() {
        // given
        groupRepository.deleteAll();
        groupRepository.save(group1);
        groupRepository.save(group2);

        // when
        groupService.deleteGroup(group1.getGroupId());

        // then
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList.size()).isEqualTo(1);
    }
}
