package az.technical.task.technicaltask.repository;

import az.technical.task.technicaltask.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer>{

    Optional<AccountEntity> findByAccountId(String accountId);

    List<AccountEntity> findAllByCustomerId(String customerId);

    Optional<AccountEntity> findByCustomerIdAndAccountId(String customerId, String accountId);

    @Query(value = "SELECT account_id FROM accounts", nativeQuery = true)
    Set<String> getAllAccountIds();
}
