package org.example.plain.domain.member.Controller;

import org.example.plain.domain.member.DTO.Member;
import org.example.plain.domain.member.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping
    public String GetAllMember(String g_id, Model model) throws Exception {
        ArrayList<Member> members = memberService.GetMembers(g_id);

        model.addAttribute("members", members);

        return "GetALlMembers";

    }

    @GetMapping
    public String GetMemberById(String g_id, String u_id, Model model) throws Exception {
        Member member = memberService.GetMember(g_id, u_id);
        model.addAttribute("member", member);
        return "GetMember";

    }

}
