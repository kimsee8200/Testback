package org.example.plain.domain.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.plain.common.ResponseField;
import org.example.plain.common.ResponseMaker;
import org.example.plain.domain.board.service.BoardServiceImpl;
import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.homework.interfaces.WorkService;
import org.example.plain.domain.homework.service.WorkMemberServiceImpl;
import org.example.plain.domain.homework.service.WorkServiceImpl;
import org.example.plain.domain.homework.dto.Work;
import org.example.plain.domain.homework.dto.WorkMember;
import org.example.plain.domain.homework.dto.WorkSubmitField;
import org.example.plain.domain.homework.dto.WorkSubmitFieldResponse;
import org.example.plain.domain.user.dto.CustomUserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;


@Tag(name = "homework", description = "과제 도메인.")
@RestController
@RequestMapping("/assignment")
public class ProjectController {

    WorkService workService;
    WorkMemberServiceImpl workMemberService;
    BoardServiceImpl boardService;

    // 수정 필요. -> 과제로.

    @PostMapping("/new_assignment")
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

    @GetMapping("/{work_id}/details")
    public ResponseEntity<ResponseField<Work>> getWorkDetail(@PathVariable(value = "work_id") String work_id) throws Exception {
        Board board = boardService.getBoard(work_id);
        workService.selectWork(work_id);
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

    @PostMapping(value = "/{work_id}/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitWork(@PathVariable String work_id, @RequestPart WorkSubmitField workSubmitField) throws Exception {
        workSubmitField.setWorkId(work_id);
        workService.submitWork(workSubmitField);
        return ResponseEntity.ok().build();
    }
}
