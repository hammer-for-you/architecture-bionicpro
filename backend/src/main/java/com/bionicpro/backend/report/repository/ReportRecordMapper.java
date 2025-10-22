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

import com.bionicpro.backend.report.model.ReportRecord;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author Maxim Nikolsky
 */
class ReportRecordMapper implements RowMapper<ReportRecord> {
    @Override
    public ReportRecord mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new ReportRecord(
                mapUser(rs),
                mapDevice(rs),
                mapMetric(rs)
        );
    }

    private ReportRecord.User mapUser(ResultSet rs) throws SQLException {
        return new ReportRecord.User(
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getString("user_phone"),
                rs.getString("user_address")
        );
    }

    private ReportRecord.Device mapDevice(ResultSet rs) throws SQLException {
        return new ReportRecord.Device(
                UUID.fromString(rs.getString("device_id")),
                rs.getString("device_name")
        );
    }

    private ReportRecord.Metric mapMetric(ResultSet rs) throws SQLException {
        return new ReportRecord.Metric(
                rs.getString("metric_value"),
                rs.getString("metric_unit"),
                rs.getTimestamp("metric_timestamp").toLocalDateTime()
        );
    }
}
