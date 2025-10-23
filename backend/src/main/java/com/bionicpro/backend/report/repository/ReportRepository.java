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
