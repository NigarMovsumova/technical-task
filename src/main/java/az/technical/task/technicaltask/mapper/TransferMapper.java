package az.technical.task.technicaltask.mapper;

import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.model.entity.TransferEntity;
import org.springframework.stereotype.Component;

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
                .description(transferEntity.getDescription())
                .build();
    }


    public TransferEntity mapDtoToEntity(TransferDto transferDto) {
        return TransferEntity
                .builder()
                .toppedUpAccountId(transferDto.getToppedUpAccountId())
                .accountId(transferDto.getAccountId())
                .customerId(transferDto.getCustomerId())
                .amount(transferDto.getAmount())
                .category(transferDto.getCategory())
                .description(transferDto.getDescription())
                .build();
    }

    public List<TransferDto> mapEntityListToDtoList(List<TransferEntity> transferEntities) {
        return transferEntities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
