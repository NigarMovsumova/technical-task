package az.technical.task.technicaltask.utils;

import az.technical.task.technicaltask.exceptions.NoSuchAccountException;
import az.technical.task.technicaltask.exceptions.WrongPaymentChoiceException;
import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import az.technical.task.technicaltask.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferUtil {

    private final AccountRepository accountRepository;

    public TransferUtil(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isTransferValid(TransferDto transferDto){
        if (transferDto.getAccountId().equals(transferDto.getToppedUpAccountId())) {
            throw new RuntimeException("Transfer can not be made to the same account");
        }

        if (transferDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer should be more than 0");
        }

        AccountEntity chargedAccount = accountRepository
                .findByAccountId(transferDto.getAccountId())
                .orElseThrow(() -> new NoSuchAccountException("Customer does not have such an account"));

        AccountEntity toppedUpAccount = accountRepository
                .findByAccountId(transferDto.getToppedUpAccountId())
                .orElseThrow(() -> new NoSuchAccountException("Such account does not exist"));

        if (chargedAccount.getAmount().compareTo(transferDto.getAmount()) < 0) {
            throw new WrongPaymentChoiceException("You do not have enough amount on your account balance for this payment");
        }

        if (!chargedAccount.getStatus().equals("ACTIVE") || !toppedUpAccount.getStatus().equals("ACTIVE")) {
            throw new WrongPaymentChoiceException("Account should be activated to make/receive a payment");
        }

        if (!chargedAccount.getCurrency().equals(toppedUpAccount.getCurrency())) {
            throw new WrongPaymentChoiceException("You can make payments only between accounts with same currencies");
        }

        return true;
    }
}
