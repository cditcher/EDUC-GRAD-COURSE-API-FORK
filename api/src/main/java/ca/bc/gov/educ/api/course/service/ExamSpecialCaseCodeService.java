package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.exception.EntityNotFoundException;
import ca.bc.gov.educ.api.course.model.dto.ExamSpecialCaseCode;
import ca.bc.gov.educ.api.course.model.entity.ExamSpecialCaseCodeEntity;
import ca.bc.gov.educ.api.course.model.transformer.ExamSpecialCaseCodeTransformer;
import ca.bc.gov.educ.api.course.repository.ExamSpecialCaseCodeRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ExamSpecialCaseCodeService {
    	
    private final ExamSpecialCaseCodeRepository examSpecialCaseCodeRepository;
    private final ExamSpecialCaseCodeTransformer examSpecialCaseCodeTransformer;

    public ExamSpecialCaseCodeService(ExamSpecialCaseCodeRepository examSpecialCaseCodeRepository, ExamSpecialCaseCodeTransformer examSpecialCaseCodeTransformer) {
        this.examSpecialCaseCodeRepository = examSpecialCaseCodeRepository;
        this.examSpecialCaseCodeTransformer = examSpecialCaseCodeTransformer;
    }

	/**
	 * Get List<ExamSpecialCaseCode>
	 *
	 * @return List<ExamSpecialCaseCode>
	 */
	@Retry(name = "generalgetcall")
	public List<ExamSpecialCaseCode> getExamSpecialCaseCodeList() {
		List<ExamSpecialCaseCode> examSpecialCaseCodes  = examSpecialCaseCodeTransformer.transformToDTO(examSpecialCaseCodeRepository.findAll());
		return sort(examSpecialCaseCodes);
	}
	
    /**
     * Get ExamSpecialCaseCode
     *
     * @param examSpecialCaseCode
     * @return Student Course
     */
	@Retry(name = "generalgetcall")
    public ExamSpecialCaseCode getExamSpecialCaseCode(String examSpecialCaseCode) {
		Optional<ExamSpecialCaseCodeEntity> entity = examSpecialCaseCodeRepository.findById(examSpecialCaseCode);
		if(entity.isPresent()) {
			return examSpecialCaseCodeTransformer.transformToDTO(entity.get());
		}
		throw new EntityNotFoundException(String.format("Exam Special Code %s not found", examSpecialCaseCode));
    }
    
    private List<ExamSpecialCaseCode> sort(List<ExamSpecialCaseCode> examSpecialCaseCodes) {
		Collections.sort(examSpecialCaseCodes, Comparator.comparing(ExamSpecialCaseCode::getLabel));
		return examSpecialCaseCodes;
    }
	
}
