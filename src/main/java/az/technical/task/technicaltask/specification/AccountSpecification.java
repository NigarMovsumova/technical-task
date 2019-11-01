package az.technical.task.technicaltask.specification;

import az.technical.task.technicaltask.model.AccountRequest;
import az.technical.task.technicaltask.model.entity.AccountEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification implements Specification<AccountEntity> {

    private AccountRequest.AccountFilter filter;
    private String customerId;

    public AccountSpecification(AccountRequest.AccountFilter filter, String customerId) {
        this.filter = filter;
        this.customerId = customerId;
    }

    @Override
    public Predicate toPredicate(Root<AccountEntity> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                criteriaBuilder.equal
                        (root.get(Field.CUSTOMER_ID),
                                customerId)
        );

        if (filter.getCurrency() != "") {
            predicates.add(
                    criteriaBuilder.equal
                            (root.get(Field.CURRENCY),
                                    filter.getCurrency())
            );
        }

        if (filter.getAccountId() != "") {
            predicates.add(criteriaBuilder.equal(root.get(Field.ACCOUNT_ID), filter.getAccountId()));
        }

        predicates.add(criteriaBuilder.between(root.get(Field.AMOUNT),
                filter.getMinAmount(), filter.getMaxAmount()));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private abstract class Field {
        private static final String CUSTOMER_ID = "customerId";
        private static final String CURRENCY = "currency";
        private static final String AMOUNT = "amount";
        private static final String ACCOUNT_ID = "accountId";

        private Field() {
        }
    }
}
