package org.example.plain.domain.board.controller;

import org.example.plain.domain.board.service.BoardServiceImpl;
import org.example.plain.domain.board.dto.Board;
import org.example.plain.domain.user.dto.UserRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class BoardController {

    BoardServiceImpl boardService;

    @GetMapping("/{board_id}")
    public ResponseEntity<Board> getBoard(@PathVariable String board_id) {
        return ResponseEntity.ok(boardService.getBoard(board_id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Board>> getGroupBoardList(@RequestParam String group_id){
        return ResponseEntity.ok().body(boardService.getGroupBoards(group_id));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertBoard(@RequestBody Board board) {
        boardService.createBoard(board);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateBoard(@RequestBody Board board, @RequestParam String board_id, UserRequestResponse userRequestResponse) {
        boardService.updateBoard(board, board_id, userRequestResponse.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{board_id}")
    public ResponseEntity<?> deleteBoard(@PathVariable String board_id, UserRequestResponse userRequestResponse) {
        boardService.deleteBoard(board_id, userRequestResponse.getId());
        return ResponseEntity.ok().build();
    }
}
