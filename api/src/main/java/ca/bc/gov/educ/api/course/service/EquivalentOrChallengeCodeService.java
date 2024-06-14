package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.exception.EntityNotFoundException;
import ca.bc.gov.educ.api.course.model.dto.EquivalentOrChallengeCode;
import ca.bc.gov.educ.api.course.model.entity.EquivalentOrChallengeCodeEntity;
import ca.bc.gov.educ.api.course.model.transformer.EquivalentOrChallengeCodeTransformer;
import ca.bc.gov.educ.api.course.repository.EquivalentOrChallengeCodeRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EquivalentOrChallengeCodeService {

    private final EquivalentOrChallengeCodeRepository equivalentOrChallengeCodeRepository;
    private final EquivalentOrChallengeCodeTransformer equivalentOrChallengeCodeTransformer;

    public EquivalentOrChallengeCodeService(EquivalentOrChallengeCodeRepository equivalentOrChallengeCodeRepository, EquivalentOrChallengeCodeTransformer equivalentOrChallengeCodeTransformer) {
        this.equivalentOrChallengeCodeRepository = equivalentOrChallengeCodeRepository;
        this.equivalentOrChallengeCodeTransformer = equivalentOrChallengeCodeTransformer;
    }

	/**
	 * Get all Equivalent Or Challenge Codes
	 *
	 * @return List<EquivalentOrChallengeCode>
	 */
	@Retry(name = "generalgetcall")
	public List<EquivalentOrChallengeCode> getEquivalentOrChallengeCodeList() {
		List<EquivalentOrChallengeCode> equivalentOrChallengeCodes  = equivalentOrChallengeCodeTransformer.transformToDTO(equivalentOrChallengeCodeRepository.findAll());
		return sort(equivalentOrChallengeCodes);
	}

    /**
     * Get EquivalentOrChallengeCode
     *
     * @param equivalentOrChallengeCode
     * @return EquivalentOrChallengeCode
     */
	@Retry(name = "generalgetcall")
    public EquivalentOrChallengeCode getEquivalentOrChallengeCode(String equivalentOrChallengeCode) throws EntityNotFoundException  {
		Optional<EquivalentOrChallengeCodeEntity> entity = equivalentOrChallengeCodeRepository.findById(equivalentOrChallengeCode);
		if(entity.isPresent()) {
			return equivalentOrChallengeCodeTransformer.transformToDTO(entity.get());
		}
		throw new EntityNotFoundException(String.format("Equivalent Or Challenge Code %s not found", equivalentOrChallengeCode));
    }
    
    private List<EquivalentOrChallengeCode> sort(List<EquivalentOrChallengeCode> equivalentOrChallengeCodes) {
		Collections.sort(equivalentOrChallengeCodes, Comparator.comparing(EquivalentOrChallengeCode::getLabel));
		return equivalentOrChallengeCodes;
    }

}
