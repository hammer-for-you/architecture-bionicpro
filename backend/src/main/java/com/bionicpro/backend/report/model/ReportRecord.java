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
 *
 * @author Maxim Nikolsky
 */
public record ReportRecord(
        User user,
        Device device,
        Metric metric
) {
    public record User(
            String name,
            String email,
            String phone,
            String address
    ) {
    }

    public record Device(
            UUID id,
            String name
    ) {
    }

    public record Metric(
            String value,
            String unit,
            LocalDateTime timestamp
    ) {
    }
}
