package org.example.plain.domain.userManage.interfaces;

import org.example.plain.domain.classLecture.dto.ClassResponse;
import org.example.plain.domain.lecture.normal.entity.Lecture;
import org.example.plain.domain.user.dto.UserRequest;

import java.util.List;

public interface UserManageService {
    public UserRequest userSingleInfo(String userId);
    public List<ClassResponse> getMyClasses(String userId);
    public List<Lecture> getMyLectures(String userId);
    public void readAlarm(boolean read);
}
