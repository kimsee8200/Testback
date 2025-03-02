package org.example.plain.scenario.homework;

import org.example.plain.domain.classLecture.dto.ClassAddRequest;
import org.example.plain.domain.classLecture.dto.ClassResponse;
import org.example.plain.domain.classLecture.service.ClassLectureService;
import org.example.plain.domain.groupmember.service.GroupMemberService;
import org.example.plain.domain.homework.Service.interfaces.WorkMemberService;
import org.example.plain.domain.homework.Service.interfaces.WorkService;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.example.plain.domain.user.dto.UserRequestResponse;
import org.example.plain.domain.user.entity.User;
import org.example.plain.domain.user.interfaces.UserService;
import org.example.plain.domain.user.repository.UserRepository;
import org.example.plain.repository.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class HomeworkTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassLectureService lectureService;

    @Autowired
    private WorkService workService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository repository;

    @Autowired
    private GroupMemberService groupMemberService;

    private List<ClassResponse> classResponse;
    private Authentication authUser;
    private Authentication authUser2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkMemberService workMemberService;


    @BeforeEach
    public void init(){
        boardRepository.deleteAll();
        userRepository.deleteAll();

        UserRequestResponse userRequestResponse = new UserRequestResponse();
        userRequestResponse.setId("adminGo");
        userRequestResponse.setUsername("admin");
        userRequestResponse.setPassword("admin");
        userRequestResponse.setEmail("admin@example.com");

        UserRequestResponse userRequestResponse2 = new UserRequestResponse();
        userRequestResponse2.setId("adminGo2");
        userRequestResponse2.setUsername("admin");
        userRequestResponse2.setPassword("admin");
        userRequestResponse2.setEmail("admin@example.com");

        User user = new User();
        user.setId("adminGo");

        User user2 = new User();
        user2.setId("adminGo2");

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        CustomUserDetails customUserDetails2 = new CustomUserDetails(user2);

        userService.createUser(userRequestResponse);
        userService.createUser(userRequestResponse2);

        authUser2 = new UsernamePasswordAuthenticationToken(customUserDetails2, "admin");
        authUser = new UsernamePasswordAuthenticationToken(customUserDetails, "admin");

        lectureService.createClass(new ClassAddRequest(null,user,"makeClass","클래스 만들기.")); // 클래스 인원 추가.

        lectureService.createClass(new ClassAddRequest(null,user2,"deleteClass","클래스 없에기."));

        classResponse = lectureService.getAllClass();
        groupMemberService.joinGroup(classResponse.get(0).id(),customUserDetails.getUser().getId());
        groupMemberService.joinGroup(classResponse.get(1).id(),customUserDetails.getUser().getId());
        groupMemberService.joinGroup(classResponse.get(1).id(),customUserDetails2.getUser().getId());
    }

    @Test
    @Rollback(value = true)
    public void WorkInsertAndFind(){
        Work work = new Work();
        work.setTitle("Work Title");
        work.setContent("Work Content");
        work.setDeadline(LocalDateTime.of(2025,3,1,23,59,59));

        workService.insertWork(work,classResponse.get(0).id(),authUser);

        List<Work> works = workService.selectGroupWorks(classResponse.get(0).id());
        List<Work> otherClassWork = workService.selectGroupWorks(classResponse.get(1).id());

        assertThat(otherClassWork).isEmpty();
        assertThat(works.get(0).getTitle()).isEqualTo("Work Title");
    }

    @Test
    @Rollback(value = true)
    public void WorkMemberInsertAndFind(){
        Work work = new Work();
        work.setTitle("Work Title");
        work.setContent("Work Content");
        work.setDeadline(LocalDateTime.of(2025,3,1,23,59,59));

        workService.insertWork(work,classResponse.get(1).id(),authUser2);
        List<Work> classWork = workService.selectGroupWorks(classResponse.get(1).id());

        System.out.println(classWork.get(0).getWorkId());
        Work work1 = workService.selectWork(classWork.get(0).getWorkId());

        CustomUserDetails username = (CustomUserDetails) authUser.getPrincipal();

        workMemberService.addHomeworkMember(work1.getWorkId(),username.getUser().getId());

        List<WorkMember> workMember = workMemberService.homeworkMembers(work1.getWorkId());

        assertThat(workMember.get(0).getUser().getId()).isEqualTo("adminGo");
        assertThat(workMember.get(0).getWork().getWorkId()).isNotNull();

        // 과제가 할당되면, 이메일 전송기능 개발 필요.
        // 과제 검색기능 할당된 과제 검색 기능 개발 필요.
    }

}
