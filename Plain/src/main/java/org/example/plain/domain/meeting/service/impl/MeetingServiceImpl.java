package org.example.plain.domain.meeting.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.plain.domain.meeting.dto.ChatMessage;
import org.example.plain.domain.meeting.dto.ParticipantState;
import org.example.plain.domain.meeting.dto.SignalMessage;
import org.example.plain.domain.meeting.service.ChatService;
import org.example.plain.domain.meeting.service.MeetingService;
import org.example.plain.domain.meeting.service.ParticipantService;
import org.example.plain.domain.meeting.service.SignalingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final ChatService chatService;
    private final ParticipantService participantService;
    private final SignalingService signalingService;

    @Override
    public void handleParticipantJoin(String roomId, String userId, String userName) {
        participantService.addParticipant(roomId, userId);
        participantService.updateParticipantState(roomId, userId, userName, false, false);
    }

    @Override
    public void handleParticipantLeave(String roomId, String userId) {
        participantService.removeParticipant(roomId, userId);
    }

    @Override
    public void handleStateChange(String roomId, String userId, String userName, boolean isMuted, boolean isVideoOff) {
        participantService.updateParticipantState(roomId, userId, userName, isMuted, isVideoOff);
    }

    @Override
    public void handleChatMessage(ChatMessage message) {
        chatService.saveMessage(message);
    }

    @Override
    public List<ParticipantState> getParticipantStates(String roomId) {
        return participantService.getParticipantStates(roomId);
    }

    @Override
    public List<ChatMessage> getChatMessages(String roomId) {
        return chatService.getMessages(roomId);
    }

    @Override
    public boolean isRoomEmpty(String roomId) {
        return participantService.isRoomEmpty(roomId);
    }

    @Override
    public void clearRoom(String roomId) {
        chatService.clearChatHistory(roomId);
    }

    @Override
    public void addParticipant(String roomId, String userId) {
        participantService.addParticipant(roomId, userId);
    }

    @Override
    public void updateParticipantState(String roomId, String userId, String userName, boolean isMuted, boolean isVideoOff) {
        participantService.updateParticipantState(roomId, userId, userName, isMuted, isVideoOff);
    }

    @Override
    public void handleSignal(SignalMessage message) {
        signalingService.handleSignal(message);
    }

    @Override
    public void saveChatMessage(ChatMessage message) {
        chatService.saveMessage(message);
    }

    @Override
    public List<String> getParticipants(String roomId) {
        return participantService.getParticipants(roomId);
    }

    @Override
    public String getOffer(String roomId) {
        return signalingService.getOffer(roomId);
    }

    @Override
    public String getAnswer(String roomId) {
        return signalingService.getAnswer(roomId);
    }

    @Override
    public List<String> getCandidates(String roomId) {
        return signalingService.getCandidates(roomId);
    }
} 