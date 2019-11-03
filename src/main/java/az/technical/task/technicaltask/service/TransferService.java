package az.technical.task.technicaltask.service;

import az.technical.task.technicaltask.exceptions.NoSuchAccountException;
import az.technical.task.technicaltask.mapper.TransferMapper;
import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import az.technical.task.technicaltask.model.entity.TransferEntity;
import az.technical.task.technicaltask.repository.AccountRepository;
import az.technical.task.technicaltask.repository.TransferRepository;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.utils.TransferUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;
    private final TransferUtil transferUtil;

    public TransferService(TransferRepository transferRepository,
                           AccountRepository accountRepository, TransferMapper transferMapper,
                           TransferUtil transferUtil) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.transferMapper = transferMapper;
        this.transferUtil = transferUtil;
    }


    public void makeTransfer(UserAuthentication userAuthentication, TransferDto transferDto) {

        TransferEntity sentTransferEntity = transferMapper.mapDtoToEntity(transferDto);
        TransferEntity receivedTransferEntity = transferMapper.mapDtoToEntity(transferDto);

        AccountEntity senderAccountEntity = accountRepository
                .findByAccountId(transferDto.getSenderAccountId())
                .orElseThrow(() -> new NoSuchAccountException("Customer does not have such an account"));
        AccountEntity receiverAccountEntity = accountRepository
                .findByAccountId(transferDto.getReceiverAccountId())
                .orElseThrow(() -> new NoSuchAccountException("Customer does not have such an account"));

        if (transferUtil.isTransferValid(transferDto, senderAccountEntity, receiverAccountEntity, userAuthentication.getPrincipal())) {
            sentTransferEntity.setIncreased(false);
            sentTransferEntity.setCurrency("AZN");
            transferRepository.save(sentTransferEntity);

            receivedTransferEntity.setIncreased(true);
            receivedTransferEntity.setCurrency("AZN");
            receivedTransferEntity.setCustomerId(receiverAccountEntity.getCustomerId());

            senderAccountEntity.setAmount(senderAccountEntity.getAmount().subtract(transferDto.getAmount()));
            receiverAccountEntity.setAmount(receiverAccountEntity.getAmount().add(transferDto.getAmount()));
            accountRepository.save(senderAccountEntity);
            accountRepository.save(receiverAccountEntity);
        }
    }

    public List<TransferDto> getAllOwnTransfers(String customerId) {
        List<TransferEntity> ownTransfersList= transferRepository.findAllByCustomerId(customerId);
        return transferMapper.mapEntityListToDtoList(ownTransfersList);
    }

    public List<TransferDto> getSentOwnTransfers(String customerId) {
        return transferMapper.mapEntityListToDtoList(transferRepository
                .findAllByCustomerIdAndIncreased(customerId, false));
    }

    public List<TransferDto> getReceivedOwnTransfers(String customerId) {
        return transferMapper.mapEntityListToDtoList( transferRepository
                .findAllByCustomerIdAndIncreased(customerId, true));
    }
}
