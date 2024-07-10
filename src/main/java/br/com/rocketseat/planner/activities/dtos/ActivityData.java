package br.com.rocketseat.planner.activities.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityData(UUID id, String title, LocalDateTime occurs_at) { }
