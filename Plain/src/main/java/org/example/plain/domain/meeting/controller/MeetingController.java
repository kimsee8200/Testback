package org.example.plain.domain.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.example.plain.common.config.SecurityUtils;
import org.example.plain.domain.meeting.dto.ChatMessage;
import org.example.plain.domain.meeting.dto.ParticipantState;
import org.example.plain.domain.meeting.service.MeetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @PostMapping("/{roomId}/join")
    public ResponseEntity<Void> joinMeeting(@PathVariable String roomId, @RequestBody Map<String, String> request) {
        String userId = SecurityUtils.getUserId();
        String userName = request.getOrDefault("user-name", userId); // 기본값으로 userId 사용
        meetingService.handleParticipantJoin(roomId, userId, userName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{roomId}/leave")
    public ResponseEntity<Void> leaveMeeting(@PathVariable String roomId) {
        String userId = SecurityUtils.getUserId();
        meetingService.handleParticipantLeave(roomId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roomId}/participants")
    public ResponseEntity<List<String>> getParticipants(@PathVariable String roomId) {
        return ResponseEntity.ok(meetingService.getParticipants(roomId));
    }

    @GetMapping("/{roomId}/states")
    public ResponseEntity<List<ParticipantState>> getParticipantStates(@PathVariable String roomId) {
        return ResponseEntity.ok(meetingService.getParticipantStates(roomId));
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable String roomId) {
        return ResponseEntity.ok(meetingService.getChatMessages(roomId));
    }

    @GetMapping("/{roomId}/offer")
    public ResponseEntity<String> getOffer(@PathVariable String roomId) {
        return ResponseEntity.ok(meetingService.getOffer(roomId));
    }

    @GetMapping("/{roomId}/answer")
    public ResponseEntity<String> getAnswer(@PathVariable String roomId) {
        return ResponseEntity.ok(meetingService.getAnswer(roomId));
    }

    @GetMapping("/{roomId}/candidates")
    public ResponseEntity<List<String>> getCandidates(@PathVariable String roomId) {
        return ResponseEntity.ok(meetingService.getCandidates(roomId));
    }
} 