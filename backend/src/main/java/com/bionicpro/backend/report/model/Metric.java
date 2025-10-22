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
