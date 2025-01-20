package domain.canlender.dao;

import domain.canlender.dto.Calender;

import java.sql.SQLException;

public interface CalenderDao {
    public Calender GetCalender(String c_id, String u_id) throws SQLException, ClassNotFoundException;
    public Boolean AddCalender(Calender calender) throws SQLException, ClassNotFoundException;
    public Boolean UpdateCalender(Calender calender) throws SQLException, ClassNotFoundException;
    public Boolean DeleteCalender(String c_id, String u_id) throws SQLException, ClassNotFoundException;
}
