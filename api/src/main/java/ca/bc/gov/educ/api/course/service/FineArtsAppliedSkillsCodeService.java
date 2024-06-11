package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.dto.FineArtsAppliedSkillsCode;
import ca.bc.gov.educ.api.course.model.transformer.FineArtsAppliedSkillsCodeTransformer;
import ca.bc.gov.educ.api.course.repository.FineArtsAppliedSkillsCodeRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FineArtsAppliedSkillsCodeService {
    	
    private final FineArtsAppliedSkillsCodeRepository fineArtsAppliedSkillsCodeRepository;
    private final FineArtsAppliedSkillsCodeTransformer fineArtsAppliedSkillsCodeTransformer;

    public FineArtsAppliedSkillsCodeService(FineArtsAppliedSkillsCodeRepository fineArtsAppliedSkillsCodeRepository, FineArtsAppliedSkillsCodeTransformer fineArtsAppliedSkillsCodeTransformer) {
        this.fineArtsAppliedSkillsCodeRepository = fineArtsAppliedSkillsCodeRepository;
        this.fineArtsAppliedSkillsCodeTransformer = fineArtsAppliedSkillsCodeTransformer;
    }

	/**
	 * Get List<FineArtsAppliedSkillsCode>
	 *
	 * @return List<FineArtsAppliedSkillsCode>
	 */
	@Retry(name = "generalgetcall")
	public List<FineArtsAppliedSkillsCode> getFineArtsAppliedSkillsCodeList() {
		List<FineArtsAppliedSkillsCode> fineArtsAppliedSkillsCodes  = fineArtsAppliedSkillsCodeTransformer.transformToDTO(fineArtsAppliedSkillsCodeRepository.findAll());
		return sort(fineArtsAppliedSkillsCodes);
	}
	
    /**
     * Get FineArtsAppliedSkillsCode
     *
     * @param fineArtsAppliedSkillsCode
     * @return Student Course
     */
	@Retry(name = "generalgetcall")
    public FineArtsAppliedSkillsCode getFineArtsAppliedSkillsCode(String fineArtsAppliedSkillsCode) {
		return fineArtsAppliedSkillsCodeTransformer.transformToDTO(fineArtsAppliedSkillsCodeRepository.findById(fineArtsAppliedSkillsCode));
    }
    
    private List<FineArtsAppliedSkillsCode> sort(List<FineArtsAppliedSkillsCode> fineArtsAppliedSkillsCodes) {
		Collections.sort(fineArtsAppliedSkillsCodes, Comparator.comparing(FineArtsAppliedSkillsCode::getLabel));
		return fineArtsAppliedSkillsCodes;
    }
	
}
