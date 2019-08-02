package com.kion.bunga.services;

import com.kion.bunga.domain.Report;

public interface ReportingService {

  Report getReportForAccountId(Long accountId);
}
