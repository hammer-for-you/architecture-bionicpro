package com.bionicpro.backend.report.repository;

import com.bionicpro.backend.report.model.ReportFilter;
import com.bionicpro.backend.report.model.ReportData;
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
    private static final String REPORT_QUERY = """
            SELECT
                user_name,
                user_email,
                user_phone,
                user_address,
                device_id,
                device_name,
                groupArray((avg_metric_value, metric_unit)) as metrics
            FROM (
                     SELECT
                         user_name,
                         user_email,
                         user_phone,
                         user_address,
                         device_id,
                         device_name,
                         metric_unit,
                         avg(CAST(metric_value AS Float64)) as avg_metric_value
                     FROM report
                     WHERE
                         user_email = ?
                       AND metric_timestamp BETWEEN ? AND ?
                     GROUP BY
                         user_name,
                         user_email,
                         user_phone,
                         user_address,
                         device_id,
                         device_name,
                         metric_unit
                     HAVING avg_metric_value IS NOT NULL
                     )
            GROUP BY
                user_name,
                user_email,
                user_phone,
                user_address,
                device_id,
                device_name
            """;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReportData> mapper = new ReportRecordMapper();

    public ReportRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReportData> findReportData(ReportFilter filter) {
        if (filter.userEmail() == null) {
            throw new IllegalArgumentException("User email is required");
        }
        return jdbcTemplate.query(REPORT_QUERY, mapper, filter.userEmail(), filter.from(), filter.to());
    }
}
