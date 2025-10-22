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

import java.util.ArrayList;
import java.util.List;

/**
 * Данные для генерации отчёта
 *
 * @param user    Данные пользователя
 * @param device  Данные протеза
 * @param metrics Метрики
 * @author Maxim Nikolsky
 */
public record ReportData(
        User user,
        Device device,
        List<Metric> metrics
) {
    public ReportData {
        if (metrics == null) {
            metrics = new ArrayList<>();
        }
    }

}
