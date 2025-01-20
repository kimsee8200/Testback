package Domain.Member.Dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import Util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class GroupMemberDaoImpl implements GroupMemberDao {

    @Override
    @Transactional
    public ArrayList<String> ReadGroupId(String groupId) throws SQLException, ClassNotFoundException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection c = jdbcConnection.customgetConnection();
        ArrayList<String> result = new ArrayList<>();
        PreparedStatement preparedStatement = c.prepareStatement("select u_id from group_member where group_id=?");
        preparedStatement.setString(1, groupId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            result.add(resultSet.getString(1));
        }
        return result;

    }
}
