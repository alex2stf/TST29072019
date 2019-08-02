package com.kion.bunga.services.impl;

import com.kion.bunga.domain.Report;
import com.kion.bunga.domain.TransactionInfo;
import com.kion.bunga.entities.Account;
import com.kion.bunga.entities.Transaction;
import com.kion.bunga.repositories.AccountRepository;
import com.kion.bunga.repositories.TransactionRepository;
import com.kion.bunga.services.ReportingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReportingServiceImpl implements ReportingService {

  final TransactionRepository transactionRepository;
  final AccountRepository accountRepository;

  public ReportingServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
  }

  @Override
  public Report getReportForAccountId(Long accountId) {
    Optional<Account> optionalAccount = accountRepository.findById(accountId);
    if (!optionalAccount.isPresent()){
      return Report.EMPTY_STATE;
    }

    Account account = optionalAccount.get();
    List<Transaction> transactionList = transactionRepository.findBySender(account);
    Report report = new Report();
    report.setIban(account.getIban());
    report.setTransactionsTotal(transactionList.size());
    List<TransactionInfo> transactionInfos = new ArrayList<>();

    for (Transaction t: transactionList){
      TransactionInfo info = new TransactionInfo();
      info.setAmount(t.getAmount());
      info.setCnp(t.getReceiver().getCnp());
      transactionInfos.add(info);
    }
    report.setTransactions(transactionInfos);

    return report;
  }
}
