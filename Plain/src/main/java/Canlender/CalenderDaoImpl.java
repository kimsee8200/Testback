package Canlender;

import Member.DTO.Member;
import Util.JdbcConnection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class CalenderDaoImpl implements CalenderDao {
    ArrayList<Member> result = new ArrayList<Member>();
    JdbcConnection jdbcConnection = new JdbcConnection();

    @Override
    @Transactional
    public Calender GetCalender(String c_id, String u_id) throws SQLException, ClassNotFoundException {
        Calender result = null;
        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement pmt = c.prepareStatement("select * from calender where c_id=? and u_id=?");
        pmt.setString(1, c_id);
        pmt.setString(2, u_id);
        ResultSet rs = pmt.executeQuery();
        if(rs.next()){
            result.setC_id(c_id);
            result.setU_id(u_id);
            result.setTitle(rs.getString("title"));
            result.setContent(rs.getString("content"));
            result.setDate_info(rs.getString("date_info"));

            rs.close();
            pmt.close();
            c.close();
        }
        return result;

    }

    @Override
    public Boolean AddCalender(Calender calender) throws SQLException, ClassNotFoundException {
        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement pmt = c.prepareStatement("insert into calender values(?,?,?,?,?)");

        pmt.setString(1, calender.getC_id());
        pmt.setString(2, calender.getU_id());
        pmt.setString(3, calender.getTitle());
        pmt.setString(4, calender.getContent());
        pmt.setString(5, calender.getDate_info());

        if(pmt.executeUpdate()>0){
            pmt.close();
            c.close();
            return true;
        }
        return false;

    }

    @Override
    public Boolean UpdateCalender(Calender calender) throws SQLException, ClassNotFoundException {
        Connection c = jdbcConnection.customgetConnection();
        PreparedStatement ps = c.prepareStatement("update Calender set date_info=?,title=?, content=? where c_id=? and u_id=?");
        ps.setString(1, calender.getDate_info());
        ps.setString(2, calender.getTitle());
        ps.setString(3, calender.getContent());
        ps.setString(4, calender.getC_id());
        ps.setString(5, calender.getU_id());

        ps.executeUpdate();

        ps.close();
        c.close();

        return true;
    }

    @Override
    public Boolean DeleteCalender(String c_id, String u_id) throws SQLException, ClassNotFoundException {
        Connection c = jdbcConnection.customgetConnection();
    }
}
