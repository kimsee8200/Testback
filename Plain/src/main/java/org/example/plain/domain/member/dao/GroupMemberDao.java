package org.example.plain.domain.member.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public interface GroupMemberDao {
    public ArrayList<String> ReadGroupId(String groupId) throws SQLException, ClassNotFoundException;
}
