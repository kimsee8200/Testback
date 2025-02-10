package org.example.plain.group;

import org.example.plain.domain.groupmember.dto.GroupMemberDTO;
import org.example.plain.domain.group.entity.Group;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.repository.GroupRepository;
import org.example.plain.repository.UserRepository;
import org.example.plain.domain.groupmember.service.GroupMemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GroupMemberImplTest {
    private Group group1;
    private Group group2;
    private User user1;
    private User user2;
    private User user3;
    private GroupMember groupMember1;
    private GroupMember groupMember2;
    private GroupMember groupMember3;
    private GroupMember groupMember4;


    @Autowired
    private GroupMemberServiceImpl groupMemberServiceImpl;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        // 그룹 생성과 그룹에 포함된 맴버 생성
        group1 = Group.builder().groupId(UUID.randomUUID().toString()).groupName("대마고").build();
        group2 = Group.builder().groupId(UUID.randomUUID().toString()).groupName("플레인").build();
        user1 = User.builder().userId(UUID.randomUUID().toString()).userName("노승준").build();
        user2 = User.builder().userId(UUID.randomUUID().toString()).userName("김준화").build();
        user3 = User.builder().userId(UUID.randomUUID().toString()).userName("이금규").build();
        groupMember1 = new GroupMember(group1, user1);
        groupMember2 = new GroupMember(group1, user2);
        groupMember3 = new GroupMember(group2, user3);
        groupMember4 = new GroupMember(group2, user2);

        // basic given
        groupRepository.deleteAll();
        userRepository.deleteAll();
        groupMemberRepository.deleteAll();
        groupRepository.save(group1);
        groupRepository.save(group2);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        groupMemberRepository.save(groupMember1);
        groupMemberRepository.save(groupMember2);
        groupMemberRepository.save(groupMember3);
        groupMemberRepository.save(groupMember4);
    }

    @Test
    public void readGroupMemberAll() {
        // when
        List<GroupMemberDTO> groupMember = groupMemberServiceImpl.readGroupMemberAll(group1.getGroupId());

        // then
        assertThat(groupMember.size()).isEqualTo(2);
//        assertThat(groupMember.get(0).getUser().getUserName()).isEqualTo(user1.getUserName());
//        assertThat(groupMember.get(1).getUser().getUserName()).isEqualTo(user2.getUserName());
    }

    @Test
    public void readGroupMember() {
        // when
        GroupMemberDTO groupMemberDTO = groupMemberServiceImpl.readGroupMember(
                group1.getGroupId(), user1.getUserId());

        // then
        assertThat(groupMemberDTO.getGroup().getGroupId()).isEqualTo(group1.getGroupId());
        assertThat(groupMemberDTO.getUser().getUserId()).isEqualTo(user1.getUserId());
    }

    @Test
    public void joinGroup() {
        // given
        User user4 = User.builder().userId(UUID.randomUUID().toString()).userName("이지희").build();
        userRepository.save(user4);

        // when
        groupMemberServiceImpl.joinGroup(
                group1.getGroupId(), user4.getUserId());

        // then
        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group1, user4);
        assertThat(groupMember).isNotNull();
        assertThat(groupMember.getGroup().getGroupId()).isEqualTo(group1.getGroupId());
        assertThat(groupMember.getUser().getUserId()).isEqualTo(user4.getUserId());
    }

    @Test
    public void quitGroup() {
        // when
        groupMemberServiceImpl.quitGroup(
                group2.getGroupId(), user3.getUserId());

        // then
        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group2, user3);
        assertThat(groupMember).isNull();
    }
}
