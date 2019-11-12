package az.technical.task.technicaltask.service;

import az.technical.task.technicaltask.client.MsAuthenticationClient;
import az.technical.task.technicaltask.controller.AccountController;
import az.technical.task.technicaltask.exceptions.NoSuchAccountException;
import az.technical.task.technicaltask.mapper.AccountMapper;
import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import az.technical.task.technicaltask.repository.AccountRepository;
import az.technical.task.technicaltask.security.CustomerAuthentication;
import az.technical.task.technicaltask.utils.AccountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final MsAuthenticationClient authenticationClient;
    private final AccountUtil accountUtil;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper, MsAuthenticationClient authenticationClient, AccountUtil accountUtil) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.authenticationClient = authenticationClient;
        this.accountUtil = accountUtil;
    }

    public List<AccountDto> getAccounts(String customerId) {
        logger.info("ActionLog.getAccountsByCustomerId.start");
        return accountMapper.mapEntityListToDtoList(
                accountRepository.findAllByCustomerId(customerId));
    }

    public AccountDto createAccount(String customerId) {
        logger.info("ActionLog.createAccount.start");
        AccountEntity accountEntity = AccountEntity
                .builder()
                .accountId(accountUtil.generateAccountId())
                .currency("AZN")
                .status("DEACTIVE")
                .amount(new BigDecimal(300))
                .customerId(customerId)
                .build();
        accountRepository.save(accountEntity);
        logger.info("ActionLog.createAccount.success");
        return accountMapper.mapEntityToDto(accountEntity);
    }

    public void activateAccount(CustomerAuthentication customerAuthentication,
                                String accountId) {

        logger.info("ActionLog.activateAccount.start");
        if (!customerAuthentication.getRole().equals("ROLE_ADMIN")) {
            throw new RuntimeException("Regular users can not activate accounts");
        }
        AccountEntity accountEntity = accountRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new NoSuchAccountException("Account does not exist"));
        if (!accountEntity.getStatus().equals("ACTIVE")) {
            accountEntity.setStatus("ACTIVE");
            accountRepository.save(accountEntity);
        }
        logger.info("ActionLog.activateAccount.success");
    }

    public void deleteAccount(String customerId, String accountId) {
        logger.info("ActionLog.deleteAccount.start");
        AccountEntity accountEntity = accountRepository
                .findByCustomerIdAndAccountId(customerId, accountId)
                .orElseThrow(() -> new NoSuchAccountException("User does not have such an account"));
        accountRepository.delete(accountEntity);
        logger.info("ActionLog.deleteAccount.end");
    }

    public AccountDto getAccount(String customerId, String accountId) {
        logger.info("ActionLog.getAccount.start");
        AccountEntity accountEntity = accountRepository
                .findByCustomerIdAndAccountId(customerId, accountId)
                .orElseThrow(() -> new NoSuchAccountException("User does not have such an account"));
        return accountMapper.mapEntityToDto(accountEntity);
    }

    public List<AccountDto> getAccounts(String token, CustomerAuthentication customerAuthentication, String email) {
        logger.info("ActionLog.getAccountsByEmail.start");
        String customerId = authenticationClient.getCustomerIdByEmail(token, email);
        return getAccounts(customerId);
    }
}
