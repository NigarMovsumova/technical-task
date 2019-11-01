package az.technical.task.technicaltask.repository;

import az.technical.task.technicaltask.model.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends CrudRepository<OperationEntity, Integer>,
        JpaSpecificationExecutor<OperationEntity> {

    //TODO replace with hibernate query
    @Query(value =
            "SELECT * FROM operations " +
                    "WHERE operations.account_id=?1",
            nativeQuery = true)
    List<OperationEntity> getOperations(String accountId);

    List<OperationEntity> getOperationEntitiesByAccountId(String accountId);
}
