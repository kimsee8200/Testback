package org.example.plain.domain.meeting.service;

import org.example.plain.domain.meeting.dto.ParticipantState;

import java.util.List;

public interface ParticipantService {
    void addParticipant(String roomId, String participantId);
    void removeParticipant(String roomId, String participantId);
    List<String> getParticipants(String roomId);
    void updateParticipantState(String roomId, String userId, String userName, boolean isMuted, boolean isVideoOff);
    List<ParticipantState> getParticipantStates(String roomId);
    boolean isRoomEmpty(String roomId);
} 