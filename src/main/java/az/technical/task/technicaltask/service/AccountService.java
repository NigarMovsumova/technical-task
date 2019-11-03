package az.technical.task.technicaltask.service;

import az.technical.task.technicaltask.client.MsAuthenticationClient;
import az.technical.task.technicaltask.exceptions.NoSuchAccountException;
import az.technical.task.technicaltask.mapper.AccountMapper;
import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import az.technical.task.technicaltask.repository.AccountRepository;
import az.technical.task.technicaltask.security.CustomerAuthentication;
import az.technical.task.technicaltask.utils.AccountUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final MsAuthenticationClient authenticationClient;
    private final AccountUtil accountUtil;

    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper, MsAuthenticationClient authenticationClient, AccountUtil accountUtil) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.authenticationClient = authenticationClient;
        this.accountUtil = accountUtil;
    }

    public List<AccountDto> getAccounts(String customerId) {
        return accountMapper.mapEntityListToDtoList(
                accountRepository.findAllByCustomerId(customerId));
    }

    public AccountDto createAccount(String customerId) {
        AccountEntity accountEntity = AccountEntity
                .builder()
                .accountId(accountUtil.generateAccountId())
                .currency("AZN")
                .status("DEACTIVE")
                .amount(new BigDecimal(300))
                .customerId(customerId)
                .build();

        accountRepository.save(accountEntity);
        return accountMapper.mapEntityToDto(accountEntity);
    }

    public void activateAccount(CustomerAuthentication customerAuthentication, String accountId) {
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
    }

    public void deleteAccount(String customerId, String accountId) {
        AccountEntity accountEntity = accountRepository
                .findByCustomerIdAndAccountId(customerId, accountId)
                .orElseThrow(() -> new NoSuchAccountException("User does not have such an account"));
        accountRepository.delete(accountEntity);
    }

    public AccountDto getAccount(String customerId, String accountId) {
        AccountEntity accountEntity = accountRepository
                .findByCustomerIdAndAccountId(customerId, accountId)
                .orElseThrow(() -> new NoSuchAccountException("User does not have such an account"));
        return accountMapper.mapEntityToDto(accountEntity);
    }

    public List<AccountDto> getAccounts(String token, CustomerAuthentication customerAuthentication, String email) {
        String customerId = authenticationClient.getCustomerIdByEmail(token, email);
        return getAccounts(customerId);
    }
}
