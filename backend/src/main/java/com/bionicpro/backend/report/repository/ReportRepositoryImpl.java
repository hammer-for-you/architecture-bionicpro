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

import com.bionicpro.backend.report.model.ReportFilter;
import com.bionicpro.backend.report.model.ReportRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Maxim Nikolsky
 */
@Repository
class ReportRepositoryImpl implements ReportRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReportRecord> mapper = new ReportRecordMapper();

    public ReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReportRecord> findReports(ReportFilter filter) {
        return jdbcTemplate.query("select * from report where metric_timestamp >= ? and metric_timestamp <= ?", mapper, filter.from(), filter.to());
    }
}
