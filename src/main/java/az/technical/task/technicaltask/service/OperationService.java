package az.technical.task.technicaltask.service;

import az.technical.task.technicaltask.exceptions.UnauthorizedAccessException;
import az.technical.task.technicaltask.mapper.OperationMapper;
import az.technical.task.technicaltask.model.OperationRequest;
import az.technical.task.technicaltask.model.dto.OperationDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import az.technical.task.technicaltask.model.entity.OperationEntity;
import az.technical.task.technicaltask.repository.AccountRepository;
import az.technical.task.technicaltask.repository.OperationRepository;
import az.technical.task.technicaltask.specification.OperationSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    public OperationService(AccountRepository accountRepository,
                            OperationRepository operationRepository,
                            OperationMapper operationMapper) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    public List<OperationDto> getOperations(String accountId, String customerId) {
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UnauthorizedAccessException("Account doesn't exist"));

        if (!accountEntity.getCustomerId().equals(customerId)) {
            throw new UnauthorizedAccessException("You can access information only for your own accounts");
        }

        List<OperationEntity> operationEntities = operationRepository.getOperations(accountId);

//        if (operationEntities.isEmpty()) {
//            throw new NoAccountOperationsException("No operations are found for this account");
//        }
        return operationMapper.mapEntityListToDtoList(operationEntities);
    }

    public List<OperationDto> getOperations(OperationRequest request, String accountId, String customerId) {

        AccountEntity accountEntity = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UnauthorizedAccessException("Account doesn't exist"));

        if (!accountEntity.getCustomerId().equals(customerId)) {
            throw new UnauthorizedAccessException("You can access information only for your own accounts");
        }
        List<OperationEntity> operationEntities = operationRepository.findAll(
                new OperationSpecification(request.getFilter(), accountId));
        return operationMapper.mapEntityListToDtoList(operationEntities);
    }
}
