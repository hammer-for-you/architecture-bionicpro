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

/**
 *
 * @author Maxim Nikolsky
 */
public record ReportFilter(
        LocalDateTime from,
        LocalDateTime to
) {
    public ReportFilter {
        if (from == null) {
            from = LocalDateTime.of(1970, 1, 1, 0, 0);
        }
        if (to == null) {
            to = LocalDateTime.of(3000, 1, 1, 0, 0);
        }
    }
}
