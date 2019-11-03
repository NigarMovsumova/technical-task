package az.technical.task.technicaltask.mapper;

import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.model.entity.TransferEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransferMapper {
    public TransferDto mapEntityToDto(TransferEntity transferEntity) {
        System.out.println(transferEntity.toString());
        return TransferDto
                .builder()
                .senderAccountId(transferEntity.getSenderAccountId())
                .receiverAccountId(transferEntity.getReceiverAccountId())
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
                .senderAccountId(transferDto.getSenderAccountId())
                .receiverAccountId(transferDto.getReceiverAccountId())
                .customerId(transferDto.getCustomerId())
                .amount(transferDto.getAmount())
                .currency(transferDto.getCurrency())
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
