package az.technical.task.technicaltask.repository;

import az.technical.task.technicaltask.model.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    List<TransferEntity> findAllByCustomerId(String customerId);
}
