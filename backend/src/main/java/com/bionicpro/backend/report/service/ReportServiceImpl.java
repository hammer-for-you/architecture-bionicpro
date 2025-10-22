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

package com.bionicpro.backend.report.service;

import com.bionicpro.backend.report.model.ReportFilter;
import com.bionicpro.backend.report.model.ReportRecord;
import com.bionicpro.backend.report.repository.ReportRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Maxim Nikolsky
 */
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ReportRecord> findReports(ReportFilter filter) {
        var currentUserEmail = getCurrentUserEmail();
        return repository.findReports(filter.withUserEmail(currentUserEmail));
    }

    public String getCurrentUserEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getToken().getClaimAsString("email");
        }
        return null;
    }
}
