package az.technical.task.technicaltask.mapper;

import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.model.dto.OperationDto;
import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import az.technical.task.technicaltask.model.entity.OperationEntity;
import az.technical.task.technicaltask.model.entity.TransferEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransferMapper {
    public TransferDto mapEntityToDto(TransferEntity transferEntity) {
        return TransferDto
                .builder()
                .accountId(transferEntity.getAccountId())
                .customerId(transferEntity.getCustomerId())
                .amount(transferEntity.getAmount())
                .category(transferEntity.getCategory())
                .currency(transferEntity.getCurrency())
                .date(LocalDateTime.now())
                .description(transferEntity.getDescription())
                .increased(transferEntity.getIncreased())
                .build();
    }

    public List<TransferDto> mapEntityListToDtoList(List<TransferEntity> transferEntities) {
        return transferEntities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
