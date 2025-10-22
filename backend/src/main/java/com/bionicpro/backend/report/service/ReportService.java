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

package com.bionicpro.backend.report.service;

import com.bionicpro.backend.report.model.ReportFilter;
import com.bionicpro.backend.report.model.ReportRecord;

import java.util.List;

/**
 * Сервис для генерации отчёта
 *
 * @author Maxim Nikolsky
 */
public interface ReportService {
    /**
     * Сгенерировать отчёт
     *
     * @param filter Фильтр для получения данных отчёта
     * @return Отчёт
     */
    List<ReportRecord> generateReport(ReportFilter filter);
}
