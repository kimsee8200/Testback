package org.example.plain.domain.member.Dao;

import org.example.plain.domain.member.DTO.Member;
import org.example.plain.util.JdbcConnection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class MemberDaoImpl implements MemberDao {

    ArrayList<Member> result = new ArrayList<Member>();
    JdbcConnection jdbcConnection = new JdbcConnection();

    @Override
    @Transactional
    public ArrayList<Member> allreadmember() throws Exception{

        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement ps = c.prepareStatement("select * from User");
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String id = rs.getString("id");
            String role = rs.getString("role");
            String name = rs.getString("name");
            String Password = rs.getString("password");
            String email = rs.getString("email");

            Member mem = new Member(id, role, name, Password, email);
            result.add(mem);
        }

        rs.close();
        ps.close();
        c.close();

        return result;
    }

    @Override
    @Transactional
    public Member readmember(String memberid) throws Exception{

        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement ps = c.prepareStatement("select * from Member where id=?");
        ps.setString(1, memberid);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            String id = rs.getString("id");
            String role = rs.getString("role");
            String name = rs.getString("name");
            String Password = rs.getString("password");
            String email = rs.getString("email");
            Member mem = new Member(id, role, name, Password, email);

            rs.close();
            ps.close();
            c.close();

            return mem;
        }

        return null;

    }

    @Override
    @Transactional
    public Boolean addmember(Member member) throws SQLException, ClassNotFoundException {

        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement ps = c.prepareStatement("insert into Member values(?,?,?,?,?)");

        ps.setString(1, member.getId());
        ps.setString(2, member.getRole());
        ps.setString(3, member.getName());
        ps.setString(4, member.getPassword());
        ps.setString(5, member.getEmail());
        ps.executeUpdate();

        ps.close();
        c.close();

        return true;

    }

    @Override
    @Transactional
    public Boolean deletemember(String id) throws Exception {
        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement ps = c.prepareStatement("delete from Member where id=?");
        ps.setString(1, id);
        ps.executeUpdate();

        ps.close();
        c.close();

        return true;
    }

    @Override
    public Boolean updatemember(Member member) throws Exception {
        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement ps = c.prepareStatement("update Member set name=?,password=?,email=? where id=?");
        ps.setString(1, member.getName());
        ps.setString(2, member.getPassword());
        ps.setString(3, member.getEmail());
        ps.setString(4, member.getId());
        ps.executeUpdate();

        ps.close();
        c.close();

        return true;
    }


}
