package az.technical.task.technicaltask.service;

import az.technical.task.technicaltask.exceptions.NoSuchAccountException;
import az.technical.task.technicaltask.mapper.AccountMapper;
import az.technical.task.technicaltask.model.AccountRequest;
import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import az.technical.task.technicaltask.repository.AccountRepository;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.specification.AccountSpecification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public String generateAccountId() {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        if (accountRepository.getAllAccountIds().contains(sb.toString())) {
            generateAccountId();
        }

        return sb.toString();
    }

    public List<AccountDto> getAccounts(String customerId) {
        return accountMapper.mapEntityListToDtoList(
                accountRepository.findAllByCustomerId(customerId));
    }


    public List<AccountDto> getAccounts(AccountRequest request, String customerId) {
        System.out.println("entered");
        List<AccountEntity> accountEntities = accountRepository.findAll(
                new AccountSpecification(request.getFilter(), customerId));
        System.out.println(customerId);
        return accountMapper.mapEntityListToDtoList(accountEntities);
    }

    public AccountDto createAccount(String customerId) {
        AccountEntity accountEntity = AccountEntity
                .builder()
                .accountId(generateAccountId())
                .currency("AZN")
                .status("DEACTIVE")
                .amount(new BigDecimal(300))
                .customerId(customerId)
                .build();

        accountRepository.save(accountEntity);
        return accountMapper.mapEntityToDto(accountEntity);
    }

    public void activateAccount(UserAuthentication userAuthentication, String accountId) {
        if (!userAuthentication.getRole().equals("ROLE_ADMIN")) {
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
}
