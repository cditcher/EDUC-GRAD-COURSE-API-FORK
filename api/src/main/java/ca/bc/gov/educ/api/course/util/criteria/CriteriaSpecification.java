package ca.bc.gov.educ.api.course.util.criteria;

import ca.bc.gov.educ.api.course.exception.GradBusinessRuleException;
import ca.bc.gov.educ.api.course.util.criteria.GradCriteria.OperationEnum;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.util.Collection;
import java.util.LinkedList;

public class CriteriaSpecification<T> implements Specification<T> {

	private static final int MAX_RESULTS_DEFAULT = 1000;
	private static final int MAX_RESULTS_DEFAULT_EXPORT_TO_EXCEL = 100000;

	private final CriteriaHelper criteriaHelper;

	public CriteriaSpecification(CriteriaHelper criteriaHelper) {
		this.criteriaHelper = criteriaHelper;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate = cb.and();
		LinkedList<Predicate> predicates = new LinkedList<Predicate>();

		Collection<GradCriteria> criteriaList = criteriaHelper.getCriteria();
		OperationEnum operation = null;
		for (GradCriteria criteria : criteriaList) {
			operation = criteria.getOperation();
			switch (operation) {
				case EQUALS:
					predicates.add(cb.equal(buildExpression(root, criteria.getColumn()), criteria.getValue()));
					break;
				case NOT_EQUALS:
					predicates.add(cb.notEqual(buildExpression(root, criteria.getColumn()), criteria.getValue()));
					break;
				case GREATER_THAN:
					predicates.add(cb.greaterThan(root.get(criteria.getColumn()), (Comparable) criteria.getValue()));
					break;
				case GREATER_THAN_EQUAL_TO:
					predicates.add(cb.greaterThanOrEqualTo(root.get(criteria.getColumn()), (Comparable) criteria.getValue()));
					break;
				case IS_NULL:
					predicates.add(cb.isNull(root.get(criteria.getColumn())));
					break;
				case IS_NOT_NULL:
					predicates.add(cb.isNotNull(root.get(criteria.getColumn())));
					break;
				case LESS_THAN:
					predicates.add(cb.lessThan(root.get(criteria.getColumn()), (Comparable) criteria.getValue()));
					break;
				case LESS_THAN_EQUAL_TO:
					predicates.add(cb.lessThanOrEqualTo(root.get(criteria.getColumn()), (Comparable) criteria.getValue()));
					break;
				case LIKE:
					predicates.add(cb.like(root.get(criteria.getColumn()), "%"+criteria.getValue().toString()+"%"));
					break;
				case STARTS_WITH_IGNORE_CASE:
					predicates.add(cb.like((Expression<String>) buildExpression(root, criteria.getColumn()), criteria.getValue() + "%"));
					break;
				case IN:
					Object value = criteria.getValue();
					if (value instanceof Integer[]) {
						In<Integer> in = (In<Integer>) cb.in(buildExpression(root, criteria.getColumn()));
						Integer[] values = (Integer[]) value;
						for (int j = 0; j < values.length; j++) {
							in.value(values[j]);
						}
						predicates.add(in);
					} else {
						In<String> in = (In<String>) cb.in(buildExpression(root, criteria.getColumn()));
						String[] values = (String[]) value;
						for (int j = 0; j < values.length; j++) {
							in.value(values[j]);
						}
						predicates.add(in);
					}
					break;
				default:
					throw new GradBusinessRuleException("Unable to determine criteria for: " + operation);
			}
		}
		if (!predicates.isEmpty()) {
			predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}
		return predicate;
	}

	/**
	 * Build an expression which supports nested properties.
	 *
	 * @param root
	 * @param column
	 * @return
	 */
	private Expression<?> buildExpression(Root<T> root, String column) {
		String[] attributes = StringUtils.split(column, ".");
		Path<?> path = root;

		for (String attribute : attributes) {
			Path<?> nextPath = path.get(attribute);
			if (nextPath instanceof PluralAttributePath) {
				From<?, ?> from = (From<?, ?>) path;
				path = from.join(attribute, JoinType.INNER);
			} else {
				path = nextPath;
			}
		}
		return path;
	}
}
