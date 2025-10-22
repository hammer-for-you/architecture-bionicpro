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

package com.bionicpro.backend.report.repository;

import com.bionicpro.backend.report.model.ReportFilter;
import com.bionicpro.backend.report.model.ReportData;

import java.util.List;

/**
 * Репозиторий для получения данных отчёта
 *
 * @author Maxim Nikolsky
 */
public interface ReportRepository {
    /**
     * Получить данные для генерации отчёта
     *
     * @param filter Фильтр для запроса данных
     * @return Коллекция данных для генерации отчёта
     */
    List<ReportData> findReportData(ReportFilter filter);
}
