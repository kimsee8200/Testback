package org.example.plain.domain.homework.dao;

import org.example.plain.domain.homework.dto.Work;

import java.util.List;

public interface WorkDao {
    void insertHomework(Work work);
    void updateHomework(Work work, String id);
    Work selectHomework(String id);
    List<Work> selectAllHomework();
    void deleteHomework(String id);
    void deleteAll();
}
