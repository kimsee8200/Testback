package Controller;

import DTO.Member.Member;
import Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class Account {

    @Autowired
    MemberService memberService;

    @PostMapping("/create")
    public String createAccount(@RequestBody Member member, Model model) throws Exception {
        boolean rs = memberService.addMember(member);
        if(rs == true) {
            model.addAttribute("member", member);
            model.addAttribute("memberinsertsuccess", true);
            return "AccountCreateSuccess";
        }
        model.addAttribute("memberinsertsuccess", false);
        return "AccountCreateFailed";
    }

    @PatchMapping("update")
    public String updateAccount(@RequestBody Member member, Model model) throws Exception {
        boolean rs = memberService.updateMember(member, member.getId());
        if(rs == true) {
            model.addAttribute("member", member);
            model.addAttribute("memberupdatesuccess", true);
            return "AccountUpdateSuccess";
        }
        model.addAttribute("memberupdatesuccess", false);
        return "AccountUpdateFailed";
    }

    @PostMapping("auth")
    public String authAccount(@RequestBody String u_id, Model model) throws Exception {
        boolean rs = memberService.readMember(u_id);
        if(rs == true) {
            model.addAttribute("memberauthsuccess", true);
            return "AccountAuthSuccess";
        }
        model.addAttribute("memberauthsuccess", false);
        return "AccountAuthFail";

    }

    @DeleteMapping
    public String deleteAccount(@RequestBody String u_id, Model model) throws Exception {
        boolean rs = memberService.deleteMember(u_id);
        if(rs == true) {
            model.addAttribute("memberdeletesuccess", true);
            return "AccountDeleteSuccess";
        }
        model.addAttribute("memberdeletesuccess", false);
        return "AccountDeleteFailed";

    }
}
