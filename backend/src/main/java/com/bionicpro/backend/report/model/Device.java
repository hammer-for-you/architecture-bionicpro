package com.bionicpro.backend.report.model;

import java.util.UUID;

/**
 * Данные протеза
 *
 * @param id   Идентификатор протеза
 * @param name Название протеза
 */
public record Device(
        UUID id,
        String name
) {
}
