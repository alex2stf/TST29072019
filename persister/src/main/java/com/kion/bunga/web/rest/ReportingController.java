package com.kion.bunga.web.rest;

import com.kion.bunga.config.Authorized;
import com.kion.bunga.domain.Report;
import com.kion.bunga.services.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingController {

  private final ReportingService reportingService;

  public ReportingController(ReportingService reportingService) {
    this.reportingService = reportingService;
  }

  @Authorized
  @GetMapping(value = "reports/account/{accountId}", produces = "application/json", consumes = "application/json")
  private Report getReportForAccountId(@PathVariable("accountId") Long accountId){
    return reportingService.getReportForAccountId(accountId);
  }
}
