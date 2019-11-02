package az.technical.task.technicaltask.service;

import az.technical.task.technicaltask.mapper.TransferMapper;
import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.repository.TransferRepository;
import az.technical.task.technicaltask.model.entity.TransferEntity;
import az.technical.task.technicaltask.security.UserAuthentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;

    public TransferService(TransferRepository transferRepository, TransferMapper transferMapper) {
        this.transferRepository = transferRepository;
        this.transferMapper = transferMapper;
    }

    public List<TransferDto> getAllTransfers(String customerId) {
        List<TransferEntity> transferEntityList= transferRepository.findAllByCustomerId(customerId);
        return transferMapper.mapEntityListToDtoList(transferEntityList);
    }
}
