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

import com.bionicpro.backend.report.model.Device;
import com.bionicpro.backend.report.model.Metric;
import com.bionicpro.backend.report.model.ReportData;
import com.bionicpro.backend.report.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Maxim Nikolsky
 */
class ReportRecordMapper implements RowMapper<ReportData> {
    @Override
    public ReportData mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new ReportData(
                mapUser(rs),
                mapDevice(rs),
                mapMetrics(rs)
        );
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("user_name"),
                rs.getString("user_email"),
                rs.getString("user_phone"),
                rs.getString("user_address")
        );
    }

    private Device mapDevice(ResultSet rs) throws SQLException {
        return new Device(
                UUID.fromString(rs.getString("device_id")),
                rs.getString("device_name")
        );
    }

    private List<Metric> mapMetrics(ResultSet rs) throws SQLException {
        Object[] array = (Object[]) rs.getArray("metrics").getArray();
        return Arrays.stream(array).map(element -> {
            var tuple = (Object[]) element;
            return new Metric((Double) tuple[0], (String) tuple[1]);
        }).toList();
    }
}
