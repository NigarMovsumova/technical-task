package az.technical.task.technicaltask.specification;

import az.technical.task.technicaltask.model.OperationRequest;
import az.technical.task.technicaltask.model.entity.OperationEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OperationSpecification implements Specification<OperationEntity> {

    private OperationRequest.OperationFilter filter;
    private String accountId;

    public OperationSpecification(OperationRequest.OperationFilter filter, String accountId) {
        this.filter = filter;
        this.accountId = accountId;
    }

    @Override
    public Predicate toPredicate(Root<OperationEntity> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        System.out.println(filter);


        predicates.add(
                criteriaBuilder.equal
                        (root.get(Field.ACCOUNTID),
                                accountId)
        );


        if (!filter.getCategory().equals("") && filter.getCategory() != null) {
            predicates.add(
                    criteriaBuilder.equal
                            (root.get(Field.CATEGORY),
                                    filter.getCategory())
            );
        }
        if (!filter.getCurrency().equals("")) {
            predicates.add(
                    criteriaBuilder.equal(root.get(Field.CURRENCY),
                            filter.getCurrency())
            );
        }
        if (!filter.getDescription().equals("")) {
            predicates.add(criteriaBuilder.equal(root.get(Field.DESCRIPTION),
                    filter.getDescription())
            );
        }

        LocalDateTime daysBefore = LocalDateTime.now().minusDays(filter.getDate());
        if (filter.getDate() == 7 || filter.getDate() == 30 || filter.getDate() == 90) {
            predicates.add(criteriaBuilder.between(root.get(Field.DATE)
                    , daysBefore, LocalDateTime.now()));
        }
        System.out.println(root.get(Field.AMOUNT).getClass().getName());
        predicates.add(criteriaBuilder.between(root.get(Field.AMOUNT)
                , filter.getMinAmount(), filter.getMaxAmount()));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private abstract class Field {

        private static final String AMOUNT = "amount";
        private static final String CATEGORY = "category";
        private static final String CURRENCY = "currency";
        private static final String DESCRIPTION = "description";
        private static final String DATE = "date";
        private static final String ACCOUNTID = "accountId";

        private Field() {
        }
    }
}
