package org.acme.dto;

import jakarta.validation.constraints.NotBlank;

public record StudentDto(
        Long id,
        String name,

        String major
) {
}
