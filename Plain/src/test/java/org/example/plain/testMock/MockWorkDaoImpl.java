package org.example.plain.testMock;


import org.example.plain.domain.homework.dao.WorkDao;
import org.example.plain.domain.homework.dto.Work;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MockWorkDaoImpl implements WorkDao {

    HashMap<String, Work> requests = new HashMap<>();

    @Override
    public void insertHomework(Work work) {
        requests.put(work.getWorkId(), work);
    }

    @Override
    public void updateHomework(Work work, String id) {
        requests.put(work.getWorkId(), work);
    }

    @Override
    public Work selectHomework(String id) {
        return requests.get(id);
    }

    @Override
    public List<Work> selectAllHomework() {
        return requests.values().stream().toList();
    }

    @Override
    public void deleteHomework(Work id) {
        requests.remove(id.getWorkId());
    }

    public void deleteAll(){
        requests.clear();
    }
}
