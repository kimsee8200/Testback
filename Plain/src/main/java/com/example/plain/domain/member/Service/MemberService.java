package com.example.plain.domain.member.Service;

import com.example.plain.domain.member.DTO.Member;
import com.example.plain.domain.member.Dao.GroupMemberDaoImpl;
import com.example.plain.domain.member.Dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MemberService {
    @Autowired MemberDao memberDao;
    @Autowired GroupMemberDaoImpl groupMemberDao;

    public ArrayList<Member> GetMembers(String g_id) throws Exception {

        ArrayList<Member> result = new ArrayList<>();
        ArrayList<Member> members = memberDao.allreadmember();
        ArrayList<String> group_ids = groupMemberDao.ReadGroupId(g_id);

        for(Member member : members) {
            if(group_ids.contains(member.getName())) {
                result.add(member);
            }
        }

        return result;
    }

    public Member GetMember(String g_id, String member_id) throws Exception {
        Member member = memberDao.readmember(member_id);
        ArrayList<String> group_ids = groupMemberDao.ReadGroupId(g_id);

        if(group_ids.contains(member.getName())) {
            return member;
        }
        return null;

    }

    public Boolean addMember(Member member) throws Exception {
        boolean result = memberDao.addmember(member);

        if(result) {
            return true;
        }
        return false;
    }

    public Boolean deleteMember(String u_id) throws Exception {
        boolean result = memberDao.deletemember(u_id);
        if(result) {
            return true;
        }
        return false;
    }

    public Boolean readMember(String u_id) throws Exception {
        Member member = memberDao.readmember(u_id);
        if(member != null) {
            return true;
        }
        return false;
    }

    public Boolean updateMember(Member member, String u_id) throws Exception {
        Member row_member = memberDao.readmember(u_id);
        if(member.getName() != null) {
            row_member.setName(member.getName());
        }
        if(member.getPassword() != null) {
            row_member.setPassword(member.getPassword());
        }
        if(member.getEmail() != null) {
            row_member.setEmail(member.getEmail());
        }
        memberDao.updatemember(row_member);
        return true;
    }


}
