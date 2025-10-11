package com.organize.dto;

import java.util.UUID;

public record ProfileResponseDTO(
    UUID id,
    String name,
    String email,
    String phone
) {}