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

    public List<AccountDto> getAccounts(String customerId) {
        System.out.println(customerId);
        return accountMapper.mapEntityListToDtoList(
                accountRepository.findAllByCustomerId(customerId));
    }


    public List<AccountDto> getAccounts(AccountRequest request, String customerId) {

        List<AccountEntity> accountEntities = accountRepository.findAll(
                new AccountSpecification(request.getFilter(), customerId));
        return accountMapper.mapEntityListToDtoList(accountEntities);
    }

    public void createAccount( String customerId) {
        System.out.println("entered");
        AccountEntity accountEntity = AccountEntity
                .builder()
                //TODO generate id somehow
                .accountId("1000")
                .currency("AZN")
                .status("DEACTIVE")
                .amount(new BigDecimal(300))
                .customerId(customerId)
                .build();
        System.out.println("build");
        accountRepository.save(accountEntity);
    }

    public void activateAccount(UserAuthentication userAuthentication, String accountId) {
        List<AccountDto> accountDtoList = getAccounts(userAuthentication.getPrincipal());
        if (!accountDtoList.contains(accountId)) {
            throw new NoSuchAccountException("Customer does not have such account");
        } else {
            AccountEntity accountEntity = accountRepository
                    .findByAccountId(accountId)
                    .orElseThrow(() -> new NoSuchAccountException("Account Not Found"));
            if (accountEntity.getStatus() != "ACTIVE") {
                accountEntity.setStatus("ACTIVE");
            }
        }
    }

    public void deleteAccount(AccountRequest accountRequest, String customerId) {
        AccountEntity accountEntity = accountRepository.findOne(
                new AccountSpecification(
                        accountRequest.getFilter(),
                        customerId)).orElseThrow(() -> new NoSuchAccountException("Account is not found"));
        accountRepository.delete(accountEntity);
    }
}
