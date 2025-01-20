package org.example.plain.domain.member.Dao;

import org.example.plain.domain.member.DTO.Member;

import java.util.ArrayList;

public interface MemberDao {
    public ArrayList<Member> allreadmember() throws Exception;
    public Member readmember(String memberid) throws Exception;
    public Boolean addmember (Member member) throws Exception;
    public Boolean deletemember(String id) throws Exception;
    public Boolean updatemember(Member member) throws Exception;
}
