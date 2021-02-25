package cn.com.agree.aweb.common.base.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public class ExampleWithRangeSpecification<T> implements Specification<T> {

  private static final long serialVersionUID = 1566375764102870990L;

  private final Example<T> example;
  private final List<Range> ranges;

  public ExampleWithRangeSpecification(Example<T> example, List<Range> ranges) {
    this.example = example;
    this.ranges = ranges;
  }

  @Nullable
  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
      CriteriaBuilder builder) {
    Predicate byExample = getExamplePredicate(root, builder);

    Predicate byRange = getRangePredicate(root, builder);
    return builder.and(byExample, byRange);
  }

  private Predicate getRangePredicate(Root<T> root, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();
    if (ranges != null) {
      for (Range range : ranges) {
        if (range.isSet()) {
          if (range.isValid()) {
            Predicate predicate = buildRangePredicate(range, root, builder);
            predicates.add(builder.or(predicate));
          } else {
            return builder.disjunction();
          }
        }
      }
    }
    return predicates.isEmpty() ? builder.conjunction()
        : builder.and(predicates.toArray(new Predicate[predicates.size()]));
  }

  private Predicate getExamplePredicate(Root<T> root, CriteriaBuilder builder) {
    return QueryByExamplePredicateBuilder
        .getPredicate(root, builder, this.example);
  }

  private Predicate buildRangePredicate(Range range, Root root, CriteriaBuilder builder) {
    if (range.isBetween()) {
      return builder.between(root.get(range.getKey()), range.getFrom(), range.getTo());
    } else if (range.isFromSet()) {
      return builder.greaterThanOrEqualTo(root.get(range.getKey()), range.getFrom());
    } else if (range.isToSet()) {
      return builder.lessThanOrEqualTo(root.get(range.getKey()), range.getTo());
    }
    return null;
  }

}
