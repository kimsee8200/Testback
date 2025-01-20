package Domain.Member.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GroupMemberDao {
    public ArrayList<String> ReadGroupId(String groupId) throws SQLException, ClassNotFoundException;
}
