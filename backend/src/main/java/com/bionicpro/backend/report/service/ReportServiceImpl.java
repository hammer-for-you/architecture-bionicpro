package com.bionicpro.backend.report.service;

import com.bionicpro.backend.report.model.Report;
import com.bionicpro.backend.report.model.ReportFilter;
import com.bionicpro.backend.report.repository.ReportRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 *
 * @author Maxim Nikolsky
 */
@Service
class ReportServiceImpl implements ReportService {
    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report generateReport(ReportFilter filter) {
        var currentUserEmail = getCurrentUserEmail();
        var data = repository.findReportData(filter.withUserEmail(currentUserEmail));
        return Report.of(data);
    }

    public String getCurrentUserEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getToken().getClaimAsString("email");
        }
        return null;
    }
}
