package org.example.plain.homework.service;

import org.example.plain.common.enums.Role;
import org.example.plain.domain.board.entity.BoardEntity;
import org.example.plain.domain.classLecture.entity.ClassLecture;
import org.example.plain.domain.groupmember.entity.GroupMember;
import org.example.plain.domain.groupmember.entity.GroupMemberId;
import org.example.plain.domain.homework.Service.interfaces.WorkService;
import org.example.plain.domain.homework.Service.serviceImpl.WorkServiceImpl;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.entity.WorkEntity;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.entity.User;
import org.example.plain.repository.BoardRepository;
import org.example.plain.repository.GroupMemberRepository;
import org.example.plain.repository.WorkMemberRepository;
import org.example.plain.repository.WorkSubmitFieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(properties = {"file.path=src/main/resources/static/files/"})
@SpringJUnitConfig
public class WorkServiceTest {

    private WorkService workService;
    private WorkMemberRepository workMemberRepository;
    private BoardRepository boardRepository;
    private WorkSubmitFieldRepository workSubmitFieldRepository;
    private GroupMemberRepository groupMemberRepository;
    private List<WorkEntity> workEntities;
    private List<Work> works;
    private List<ClassLecture> classLectures;
    private List<User> users;

    @BeforeEach
    public void init(){
        workMemberRepository = Mockito.mock(WorkMemberRepository.class);
        boardRepository = Mockito.mock(BoardRepository.class);
        workSubmitFieldRepository = Mockito.mock(WorkSubmitFieldRepository.class);
        groupMemberRepository = Mockito.mock(GroupMemberRepository.class);
        workService = new WorkServiceImpl(boardRepository, workSubmitFieldRepository, workMemberRepository, groupMemberRepository);


        works =  new ArrayList<>();
        works.add(new Work("rlaqhfem","flsals","parkdea","123","스프링 공부하기","당장 일해라", LocalDateTime.of(2025, Month.FEBRUARY,28,12,00)));
        workEntities = new ArrayList<>();
        workEntities.add(WorkEntity.workToWorkEntity(works.get(0)));

        users = new ArrayList<>();
        users.add(new User("kimsee", Role.NORMAL,"김말년","1111","kim@gmail.com"));
        users.add(new User("parkdea", Role.LEADER_CLASS,"박대철","1111","park@gmail.com"));

        classLectures = new ArrayList<>();
        classLectures.add(new ClassLecture("class1","로동 랜드",null,"로동을 배우는 클래스","1212",users.get(1)));

        Mockito.when(groupMemberRepository.findById(new GroupMemberId("class1","parkdea"))).thenReturn(Optional.of(new GroupMember(classLectures.get(0), users.get(1))));
    }

    @Test
    public void saveWork(){
        CustomUserDetails customUserDetails = new CustomUserDetails(users.get(1));
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,"1234",null);

        Work work = new Work();

        ArgumentCaptor<WorkEntity> argumentCaptor =  ArgumentCaptor.forClass(WorkEntity.class);
        workService.insertWork(work,"class1",authentication);

        Mockito.verify(boardRepository).save(Mockito.any(BoardEntity.class));
        Mockito.verify(boardRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getWorkId()).isNotNull();
        assertThat(argumentCaptor.getValue().getUserId()).isEqualTo("parkdea");
        assertThat(argumentCaptor.getValue().getBoardId()).isNotNull();
    }

    @Test
    public void updateWork(){
        WorkEntity mock = WorkEntity.workToWorkEntity(works.get(0));
        mock.setUser(users.get(1));
        mock.setGroup(classLectures.get(0));

        Mockito.when(boardRepository.findByWorkId(Mockito.any())).thenReturn(Optional.of(mock));

        CustomUserDetails customUserDetails = new CustomUserDetails(users.get(1));
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,"1234",null);

        Work work = new Work();
        workService.updateWork(work,works.get(0).getWorkId(),customUserDetails.getUser().getId());

        Mockito.verify(boardRepository).save(Mockito.any(BoardEntity.class));
    }

    @Test
    public void updateBlockWork(){
        WorkEntity mock = WorkEntity.workToWorkEntity(works.get(0));
        mock.setUser(users.get(1));
        mock.setGroup(classLectures.get(0));

        Mockito.when(boardRepository.findByWorkId(Mockito.any())).thenReturn(Optional.of(mock));

        CustomUserDetails customUserDetails = new CustomUserDetails(users.get(0));
        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails,"1234",null);

        Work work = new Work();
        assertThrows(HttpClientErrorException.class,() ->
                        workService.updateWork(work,works.get(0).getWorkId(),customUserDetails.getUser().getId()
        ));

        Mockito.verify(boardRepository, Mockito.never()).save(Mockito.any(BoardEntity.class));
    }

}
