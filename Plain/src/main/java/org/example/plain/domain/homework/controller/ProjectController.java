package org.example.plain.domain.homework.controller;

import org.example.plain.common.ResponseBody;
import org.example.plain.common.ResponseMaker;
import org.example.plain.domain.board.service.BoardServiceImpl;
import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.homework.Service.serviceImpl.WorkMemberServiceImpl;
import org.example.plain.domain.homework.Service.serviceImpl.WorkServiceImpl;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.dto.WorkSubmitFieldResponse;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    WorkServiceImpl workService;
    WorkMemberServiceImpl workMemberService;
    BoardServiceImpl boardService;

    @PostMapping("/new_project")
    public ResponseEntity<?> projectInsert(Work work, String groupId, Authentication authentication){
        workService.insertWork(work, groupId, authentication);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{board_id}")
    public ResponseEntity<?> projectUpdate(Work work, @PathVariable String board_id, Authentication auth){
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        workService.updateWork(work,board_id, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{work_id}")
    public ResponseEntity<?> projectDelete(@PathVariable String work_id){
        workService.deleteWork(work_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{board_id}/details")
    public ResponseEntity<ResponseBody<Work>> getWorkDetail(@PathVariable String board_id) throws Exception {
        Board board = boardService.getBoard(board_id);
        if(!(board instanceof Work)){
            throw new Exception();
        }
        return new ResponseMaker<Work>().ok((Work) board);
    }

    @GetMapping("/{work_id}/members")
    public ResponseEntity<List<WorkMember>> getWorkMembers(@PathVariable String work_id) throws Exception {
        return ResponseEntity.ok().body(workMemberService.homeworkMembers(work_id));
    }

    @GetMapping("/{work_id}/submits")
    public ResponseEntity<List<WorkSubmitFieldResponse>> getWorkSubmitFields(@PathVariable String work_id) throws Exception {
        return ResponseEntity.ok().body(workService.getSubmitList(work_id));
    }

    @GetMapping("/{work_id}/{user_id}/submits")
    public ResponseEntity<List<File>> getWorkSubmitField(@PathVariable String work_id, @PathVariable String user_id) throws Exception {
        return ResponseEntity.ok().body(workService.getWorkResults(work_id,user_id));
    }

    @GetMapping("/single_file/{filename}")
    public ResponseEntity<File> getSingleFile(@PathVariable String filename) throws Exception {
        return ResponseEntity.ok().body(workService.getFile(filename));
    }

    @PostMapping("/{work_id}/submit")
    public ResponseEntity<?> submitWork(@PathVariable String work_id, WorkSubmitField workSubmitField) throws Exception {
        workSubmitField.setWorkId(work_id);
        workService.submitWork(workSubmitField);
        return ResponseEntity.ok().build();
    }
}
