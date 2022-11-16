package ca.bc.gov.educ.api.course.util.criteria;

import ca.bc.gov.educ.api.course.util.criteria.GradCriteria.OperationEnum;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.*;

public class CriteriaHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MAX_RESULTS_DEFAULT = 1000;

	private static final Logger LOGGER = Logger.getLogger(CriteriaHelper.class);
	
	private List<GradCriteria> criteriaList = new ArrayList<>();

	private List<Sort.Order> orderBy = new ArrayList<>();
	
	private Pageable maxResults = null;

	/**
	 * Creates the Criterias needed for Criteria Query
	 * This version should only be used where no value is required (e.g. IS_NOT_NULL, IS_NULL)
	 * @param column
	 * @param operation
	 */
	public CriteriaHelper add(String column, OperationEnum operation) {
		return add(column, operation, null);
		
	}
	
	/**
	 * Creates the Criterias needed for Criteria Query
	 * Will check for nulls and blanks before adding.
	 * @param column
	 * @param operation
	 * @param value
	 */
	public CriteriaHelper add(String column, OperationEnum operation, Object value) {
		if (operation == OperationEnum.IS_NOT_NULL || operation == OperationEnum.IS_NULL) {
			//IS_NULL and IS_NOT_NULL do not need a value
			LOGGER.debug(String.format("Adding Operation: [%s] for column: [%s]", operation.name(), column));
			criteriaList.add(new GradCriteria(column, operation, value));
		} else {
			if (value != null) {
				if (value instanceof String) {
					if (StringUtils.isNotBlank((String)value)) {
						LOGGER.debug(String.format("Adding value: [%s] with Operation: [%s] for column: [%s]", value, operation.name(), column));
						criteriaList.add(new GradCriteria(column, operation, value));
					}
				} else {
					LOGGER.debug(String.format("Adding value: [%s] with Operation: [%s] for column: [%s]", value, operation.name(), column));
					criteriaList.add(new GradCriteria(column, operation, value));
				}
			}
		}
		return this;
		
	}
	
	/**
	 * Order by the specified column (can be nested).
	 * 
	 * @param column
	 * @param asc 	true for ASC sort, false for DESC sort
	 */
	public void orderBy(String column, boolean asc) {
		if (asc)
			orderBy.add(Sort.Order.asc(column));
		else
			orderBy.add(Sort.Order.desc(column));
	}
	
	Collection<GradCriteria> getCriteria() {
		return this.criteriaList;
	}

	public Sort getSortBy() {
		return Sort.by(orderBy);
	}

	public Pageable getMaxResults() {
		if (maxResults == null) {
			return Pageable.ofSize(MAX_RESULTS_DEFAULT);
		}
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = Pageable.ofSize(maxResults);
	}

}
