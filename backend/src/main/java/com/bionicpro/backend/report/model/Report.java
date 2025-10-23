package com.bionicpro.backend.report.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Отчёт
 *
 * @param user          Пользователь
 * @param deviceMetrics Коллекция метрик устройств пользователя
 * @author Maxim Nikolsky
 */
public record Report(
        User user,
        List<DeviceMetrics> deviceMetrics
) {
    /**
     * Метрики устройства пользователя
     *
     * @param device  Данные устройства
     * @param metrics Коллекция метрик
     */
    public record DeviceMetrics(
            Device device,
            List<Metric> metrics
    ) {
        public DeviceMetrics {
            if (metrics == null) {
                metrics = new ArrayList<>();
            }
        }
    }

    /**
     * Создать экземпляр {@link Report}
     *
     * @param data Данные для создания отчёта
     * @return Новый экземпляр {@link Report}
     */
    public static Report of(List<ReportData> data) {
        if (data.isEmpty()) {
            return null;
        }
        var user = data.get(0).user();
        var deviceMetrics = data.stream().map(element -> new DeviceMetrics(element.device(), element.metrics())).toList();
        return new Report(user, deviceMetrics);
    }

    /**
     * Создать текстовое представление отчёта
     *
     * @return Текстовое представление отчёта
     */
    public String asText() {
        var builder = new StringBuilder();

        builder.append("====================================================\n");
        builder.append("Пользователь\n");
        builder.append("Имя: ").append(user.name()).append("\n");
        builder.append("Адрес: ").append(user.address()).append("\n");
        builder.append("Телефон: ").append(user.phone()).append("\n");

        if (deviceMetrics.isEmpty()) {
            builder.append("Данные по устройствам пользователя отсутствуют\n");
        } else {
            for (var deviceMetrics : deviceMetrics) {
                builder.append(generateDeviceSection(deviceMetrics));
            }
        }

        builder.append("====================================================\n");

        return builder.toString();
    }

    private String generateDeviceSection(DeviceMetrics deviceMetrics) {
        var builder = new StringBuilder();

        builder.append("====================================================\n");
        builder.append("Устройство:\n");
        builder.append("Идентификатор: ").append(deviceMetrics.device().id()).append("\n");
        builder.append("Название: ").append(deviceMetrics.device().name()).append("\n");
        builder.append("Показатели: \n");

        var metrics = deviceMetrics.metrics();
        if (metrics.isEmpty()) {
            builder.append("   Данные о показателях отсутствуют\n");
        } else {
            builder.append(String.format("%-30s | %-20s%n", "Среднее значение показателя", "Единица измерения"));
            builder.append("-------------------------------|--------------------\n");

            for (var metric : metrics) {
                builder.append(String.format("%-30.2f | %-20s%n",
                        metric.averageValue(),
                        metric.unit()));
            }
        }

        return builder.toString();
    }
}
