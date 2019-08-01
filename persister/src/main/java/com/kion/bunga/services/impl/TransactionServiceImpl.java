package com.kion.bunga.services.impl;

import com.kion.bunga.domain.Proto.Payment;
import com.kion.bunga.domain.Proto.TRANSACTION_STATUS;
import com.kion.bunga.domain.Proto.TransactionResponse;
import com.kion.bunga.entities.Account;
import com.kion.bunga.entities.Transaction;
import com.kion.bunga.repositories.AccountRepository;
import com.kion.bunga.repositories.TransactionRepository;
import com.kion.bunga.services.TransactionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

@Service
public class TransactionServiceImpl implements TransactionService {
  private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final Jedis jedis;

  public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, Jedis jedis) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.jedis = jedis;

    //test data
    Account account = accountRepository.findByCnp("1970827051573");
    if (account == null){
      account = new Account();
      account.setCnp("1970827051573");
      account.setIban("123456789012345678901234");
      account.setName("test_account_1");
      accountRepository.save(account);
    }

    account = accountRepository.findByCnp("1930726238901");

    if (account == null){
      account = new Account();
      account.setCnp("1930726238901");
      account.setIban("123456789012345678901230");
      account.setName("test_account_2");
      accountRepository.save(account);
    }

  }

  @Transactional
  @Override
  public TransactionResponse save(Payment payment) {

    TransactionResponse.Builder transactionResponseBuilder = TransactionResponse.newBuilder();

    //first check for existence. This check should be cached
    if (transactionExists(payment.getUid())){
      transactionResponseBuilder.setStatus(TRANSACTION_STATUS.ALREADY_DONE)
          .setMessage("transaction done");
      return transactionResponseBuilder.build();
    }

    AccountWrapper wrapper = findParticipants(payment);
    if (wrapper == null){
      transactionResponseBuilder.setStatus(TRANSACTION_STATUS.DENIED)
          .setMessage("participants not found");
      return transactionResponseBuilder.build();
    }



    Transaction transaction = new Transaction();
    transaction.setSender(wrapper.sender);
    transaction.setReceiver(wrapper.receiver);
    transaction.setAmount(payment.getAmount());
    transaction.setId(payment.getUid());
    transactionRepository.save(transaction);

    transactionResponseBuilder.setStatus(TRANSACTION_STATUS.DONE).setMessage("SUCCESS");

    jedisPut(transaction);
    return transactionResponseBuilder.build();
  }

  private void jedisPut(Transaction transaction){
    try {
      jedis.set(transaction.getId(), "true");
    } catch (Exception ex){
      log.info("Failed to put to jedis because {}", ex.getMessage());
    }
  }

  private String jedisGet(String id){
    try {
      return jedis.get(id);
    } catch (Exception ex){
      log.info("Failed to get from jedis because {}", ex.getMessage());
      return null;
    }
  }


  boolean transactionExists(String id){
    if ("true".equals(jedisGet(id))) {
      return true;
    }
    Optional<Transaction> t = transactionRepository.findById(id);
    if (t.isPresent()){
      return t.isPresent();
    }
    return false;
  }

  Account findByCNP(String cnp){
    return accountRepository.findByCnp(cnp);
  }

  Account findByIban(String iban){
    return accountRepository.findByIban(iban);
  }

  AccountWrapper findParticipants(Payment payment){
    String senderKey = payment.getSender();
    String receiverKey = payment.getReceiver();
    if (!StringUtils.hasText(senderKey) || !StringUtils.hasText(receiverKey)){
      return null;
    }
    Account sender = null;
    Account receiver = null;
    switch (payment.getTransactionType()){
      case WALLET_TO_WALLET:
        sender = findByCNP(senderKey);
        receiver = findByCNP(receiverKey);
        break;
      case WALLET_TO_IBAN:
        sender = findByCNP(senderKey);
        receiver = findByIban(receiverKey);
        break;
      case IBAN_TO_WALLET:
        sender = findByIban(senderKey);
        receiver = findByCNP(receiverKey);
        break;
      case IBAN_TO_IBAN:
        sender = findByIban(senderKey);
        receiver = findByIban(receiverKey);
        break;
    }
    if (sender != null && receiver != null) {
      return new AccountWrapper(sender, receiver);
    }
    return null;
  }

  class AccountWrapper {
    final Account sender;
    final Account receiver;

    AccountWrapper(Account sender, Account receiver) {
      this.sender = sender;
      this.receiver = receiver;
    }
  }

}
