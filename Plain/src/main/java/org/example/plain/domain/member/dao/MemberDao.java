package org.example.plain.domain.member.dao;

import org.example.plain.domain.member.dto.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MemberDao {
    public ArrayList<Member> allreadmember() throws Exception;
    public Member readmember(String memberid) throws Exception;
    public Boolean addmember (Member member) throws Exception;
    public Boolean deletemember(String id) throws Exception;
    public Boolean updatemember(Member member) throws Exception;
}
