package az.technical.task.technicaltask.utils;

import az.technical.task.technicaltask.exceptions.NoSuchAccountException;
import az.technical.task.technicaltask.exceptions.WrongPaymentChoiceException;
import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferUtil {

    public boolean isTransferValid(TransferDto transferDto,
                                   AccountEntity senderAccountEntity,
                                   AccountEntity receiverAccountEntity,
                                   String customerId) {
        if (!customerId.equals(transferDto.getCustomerId())) {
            throw new NoSuchAccountException("Customer can only pay from own accounts");
        }

        if (transferDto.getSenderAccountId().equals(transferDto.getReceiverAccountId())) {
            throw new WrongPaymentChoiceException("Transfer can not be made to the same account");
        }

        if (transferDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new WrongPaymentChoiceException("Transfer should be more than 0");
        }

        if (senderAccountEntity.getAmount().compareTo(transferDto.getAmount()) < 0) {
            throw new WrongPaymentChoiceException("You do not have enough amount on your account balance for this payment");
        }

        if (!senderAccountEntity.getStatus().equals("ACTIVE") || !receiverAccountEntity.getStatus().equals("ACTIVE")) {
            throw new WrongPaymentChoiceException("Account should be activated to make/receive a payment");
        }

        if (!senderAccountEntity.getCurrency().equals(receiverAccountEntity.getCurrency())) {
            throw new WrongPaymentChoiceException("You can make payments only between accounts with same currencies");
        }
        return true;
    }
}
