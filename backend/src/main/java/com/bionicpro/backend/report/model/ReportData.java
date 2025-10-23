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
