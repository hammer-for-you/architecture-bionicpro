package com.bionicpro.backend.report.service;

import com.bionicpro.backend.report.model.Report;
import com.bionicpro.backend.report.model.ReportFilter;

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
    Report generateReport(ReportFilter filter);
}
