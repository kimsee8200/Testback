package org.example.plain.domain.meeting.service;

import org.example.plain.domain.meeting.dto.ChatMessage;
import org.example.plain.domain.meeting.dto.ParticipantState;
import org.example.plain.domain.meeting.dto.SignalMessage;

import java.util.List;

public interface MeetingService {
    void handleParticipantJoin(String roomId, String userId, String userName);
    void handleParticipantLeave(String roomId, String userId);
    void handleStateChange(String roomId, String userId, String userName, boolean isMuted, boolean isVideoOff);
    void handleChatMessage(ChatMessage message);
    List<ParticipantState> getParticipantStates(String roomId);
    List<ChatMessage> getChatMessages(String roomId);
    boolean isRoomEmpty(String roomId);
    void clearRoom(String roomId);
    
    // 추가된 메서드들
    void addParticipant(String roomId, String userId);
    void updateParticipantState(String roomId, String userId, String userName, boolean isMuted, boolean isVideoOff);
    void handleSignal(SignalMessage message);
    void saveChatMessage(ChatMessage message);
    List<String> getParticipants(String roomId);
    
    // 컨트롤러에서 사용하는 메서드들
    String getOffer(String roomId);
    String getAnswer(String roomId);
    List<String> getCandidates(String roomId);
} 