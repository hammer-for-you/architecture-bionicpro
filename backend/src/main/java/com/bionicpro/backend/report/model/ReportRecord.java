/*
 * Copyright (c) 2025 FORS Development Center
 * Trifonovskiy tup. 3, Moscow, 129272, Russian Federation
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * FORS Development Center ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with FORS.
 */

package com.bionicpro.backend.report.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Данные для генерации отчёта
 *
 * @param user   Данные пользователя
 * @param device Данные протеза
 * @param metric Метрика
 * @author Maxim Nikolsky
 */
public record ReportRecord(
        User user,
        Device device,
        Metric metric
) {
    /**
     * Данные пользователя
     *
     * @param name    Имя пользователя
     * @param email   Email пользователя
     * @param phone   Телефон пользователя
     * @param address Адрес пользователя
     */
    public record User(
            String name,
            String email,
            String phone,
            String address
    ) {
    }

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

    /**
     * Метрика, полученная от протеза
     *
     * @param value     Значение метрики
     * @param unit      Единица измерения метрики
     * @param timestamp Дата и время получения метрики
     */
    public record Metric(
            String value,
            String unit,
            LocalDateTime timestamp
    ) {
    }
}
