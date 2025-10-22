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
 * Данные пользователя
 *
 * @param name    Имя пользователя
 * @param email   Email пользователя
 * @param phone   Телефон пользователя
 * @param address Адрес пользователя
 */
public record User(
        String name,
        String email,
        String phone,
        String address
) {
}
