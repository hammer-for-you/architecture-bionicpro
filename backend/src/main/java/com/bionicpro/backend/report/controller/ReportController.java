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

package com.bionicpro.backend.report.controller;

import com.bionicpro.backend.report.model.ReportFilter;
import com.bionicpro.backend.report.service.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Maxim Nikolsky
 */
@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    @PreAuthorize("hasAuthority('prothetic_user')")
    public ResponseEntity<InputStreamResource> findReports(@RequestParam(required = false) LocalDateTime from, @RequestParam(required = false) LocalDateTime to) {
        var report = reportService.generateReport(new ReportFilter(null, from, to));

        var inputStream = new ByteArrayInputStream(report.asText().getBytes(StandardCharsets.UTF_8));

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + generateFilename());
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(inputStream));
    }

    private String generateFilename() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return "report_" + LocalDateTime.now().format(formatter) + ".txt";
    }
}
