package az.technical.task.technicaltask.service;

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
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<TransferDto> getOwnTransfers(String customerId) {
        List<TransferEntity> transferEntityList = transferRepository.findAllByCustomerId(customerId);
        return transferMapper.mapEntityListToDtoList(transferEntityList);
    }

    public void makeTransfer(UserAuthentication userAuthentication, TransferDto transferDto) {

        //TODO transfer has to be saved both for sender and receiver
        if (transferUtil.isTransferValid(transferDto)){
            TransferEntity transferEntity = TransferEntity
                    .builder()
                    .accountId(
                            transferDto.getAccountId())
                    .amount(transferDto.getAmount())
                    .category(transferDto.getCategory())
                    .currency("AZN")
                    .toppedUpAccountId(transferDto.getToppedUpAccountId())
                    .customerId(userAuthentication.getPrincipal())
                    .description(transferDto.getDescription())
                    .increased(false)
                    .build();
            transferRepository.save(transferEntity);
        }
    }

    public List<TransferDto> getSentTransfers(String customerId) {
        List<AccountEntity> accountEntityList= accountRepository.findAllByCustomerId(customerId);
        Set<String> accountIds= accountEntityList
                .stream()
                .map(AccountEntity::getAccountId)
                .collect(Collectors.toSet());
        System.out.println(accountIds.toString()  );

        return null;
    }
}
