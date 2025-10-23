package com.bionicpro.backend.report.model;

/**
 * Метрика, полученная от протеза
 *
 * @param averageValue Среднее значение метрики
 * @param unit         Единица измерения метрики
 */
public record Metric(
        Double averageValue,
        String unit
) {
}
