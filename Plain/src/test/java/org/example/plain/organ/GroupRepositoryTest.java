package org.example.plain.organ;

import org.example.plain.entity.Group;
import org.example.plain.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class GroupRepositoryTest {
    private Group group1;
    private Group group2;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testClass() {
        System.out.println("__________" + groupRepository.getClass().getName());
    }

    @BeforeEach
    void setup() {
        // build() 키워드로 Group 객체 최종 반환
        group1 = Group.builder().groupId("1").groupName("플레인").build();
        group2 = Group.builder().groupId("2").groupName("심볼즈").build();
    }

    @Test
    public void createAddRead() {
        groupRepository.deleteAll();

        // given
        groupRepository.save(this.group1); // DB에 Group 데이터가 저장되도록
        groupRepository.save(this.group2);

        // when
        Optional<Group> test1 = groupRepository.findById(this.group1.getGroupId()); // select로 가져오기
        Optional<Group> test2 = groupRepository.findById(this.group2.getGroupId());

        // then
        assertThat(test1.isPresent()).isTrue();
        assertThat(test2.isPresent()).isTrue();

        assertThat(test1.get().getGroupId()).isEqualTo(this.group1.getGroupId());
        assertThat(test1.get().getGroupName()).isEqualTo(this.group1.getGroupName());
        assertThat(test2.get().getGroupId()).isEqualTo(this.group2.getGroupId());
        assertThat(test2.get().getGroupName()).isEqualTo(this.group2.getGroupName());
    }

    @Test
    public void update() {
        groupRepository.deleteAll();

        // given
        groupRepository.save(this.group1);
        groupRepository.save(this.group2);

        // when
        // id와 업데이트된 이름 받기
        String id = "1";
        Group updatedGroupData = Group.builder().groupId("1").groupName("용암중").build();
        Group updatedGroup = Group.builder().groupId(id).groupName(updatedGroupData.getGroupName()).build();
        groupRepository.save(updatedGroup);

        // then
        Optional<Group> test1 = groupRepository.findById(id);

        assertThat(test1.isPresent()).isTrue();
        assertThat(test1.get().getGroupId()).isEqualTo(id);
        assertThat(test1.get().getGroupName()).isEqualTo(updatedGroupData.getGroupName());
    }
}
