package com.bionicpro.backend.report.model;

import java.time.LocalDateTime;

/**
 * Фильтр для получения даннызх отчёта
 *
 * @param userEmail Email пользователя
 * @param from      Дата, начиная с которой запрашиваются данные
 * @param to        Дата, заканчивая которой запрашиваются данные
 * @author Maxim Nikolsky
 */
public record ReportFilter(
        String userEmail,
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

    public ReportFilter withUserEmail(String userEmail) {
        return new ReportFilter(userEmail, from, to);
    }
}
