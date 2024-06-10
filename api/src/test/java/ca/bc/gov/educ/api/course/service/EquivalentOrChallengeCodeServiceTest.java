package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.entity.EquivalentOrChallengeCodeEntity;
import ca.bc.gov.educ.api.course.repository.EquivalentOrChallengeCodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EquivalentOrChallengeCodeServiceTest {

	@Autowired
	private EquivalentOrChallengeCodeService equivalentOrChallengeCodeService;

	@MockBean
	private EquivalentOrChallengeCodeRepository equivalentOrChallengeCodeRepository;
	
	@Test
	public void testGetEquivalentOrChallengeCodeList() {
		List<EquivalentOrChallengeCodeEntity> equivalentOrChallengeCodes = new ArrayList<>();
		EquivalentOrChallengeCodeEntity obj = new EquivalentOrChallengeCodeEntity();
		obj.setEquivalentOrChallengeCode("equivalentOrChallengeCode");
		obj.setDescription("EquivalentOrChallengeCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		equivalentOrChallengeCodes.add(obj);
		Mockito.when(equivalentOrChallengeCodeRepository.findAll()).thenReturn(equivalentOrChallengeCodes);
		var result = equivalentOrChallengeCodeService.getEquivalentOrChallengeCodeList();
		assertNotNull(result);
		assertThat(result.isEmpty()).isFalse();
	}
	
	@Test
	public void testGetEquivalentOrChallengeCode() {
		EquivalentOrChallengeCodeEntity obj = new EquivalentOrChallengeCodeEntity();
		obj.setEquivalentOrChallengeCode("equivalentOrChallengeCode");
		obj.setDescription("EquivalentOrChallengeCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(equivalentOrChallengeCodeRepository.findById("equivalentOrChallengeCode")).thenReturn(Optional.of(obj));
		var result = equivalentOrChallengeCodeService.getEquivalentOrChallengeCode("equivalentOrChallengeCode");
		assertNotNull(result);
	}
	
	@Test
	public void testGetEquivalentOrChallengeCode_noContent() {
		Mockito.when(equivalentOrChallengeCodeRepository.findById("equivalentOrChallengeCode")).thenReturn(Optional.empty());
		var result = equivalentOrChallengeCodeService.getEquivalentOrChallengeCode("equivalentOrChallengeCode");
		assertNotNull(result);
	}
}
