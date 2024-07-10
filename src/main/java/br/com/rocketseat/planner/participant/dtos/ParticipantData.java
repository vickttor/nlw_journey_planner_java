package br.com.rocketseat.planner.participant.dtos;

import java.util.UUID;

public record ParticipantData(UUID id, String name, String email, Boolean isConfirmed){}
