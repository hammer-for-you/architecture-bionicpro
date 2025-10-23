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
