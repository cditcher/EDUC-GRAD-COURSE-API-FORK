package ca.bc.gov.educ.api.course.service;

import ca.bc.gov.educ.api.course.model.entity.FineArtsAppliedSkillsCodeEntity;
import ca.bc.gov.educ.api.course.repository.FineArtsAppliedSkillsCodeRepository;
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
public class FineArtsAppliedSkillsCodeServiceTest {

	@Autowired
	private FineArtsAppliedSkillsCodeService fineArtsAppliedSkillsCodeService;

	@MockBean
	private FineArtsAppliedSkillsCodeRepository fineArtsAppliedSkillsCodeRepository;
	
	@Test
	public void testGetFineArtsAppliedSkillsCodeList() {
		List<FineArtsAppliedSkillsCodeEntity> fineArtsAppliedSkillsCodes = new ArrayList<>();
		FineArtsAppliedSkillsCodeEntity obj = new FineArtsAppliedSkillsCodeEntity();
		obj.setFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		obj.setDescription("FineArtsAppliedSkillsCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		fineArtsAppliedSkillsCodes.add(obj);
		Mockito.when(fineArtsAppliedSkillsCodeRepository.findAll()).thenReturn(fineArtsAppliedSkillsCodes);
		var result = fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCodeList();
		assertNotNull(result);
		assertThat(result).isNotEmpty();
	}
	
	@Test
	public void testGetFineArtsAppliedSkillsCode() {
		FineArtsAppliedSkillsCodeEntity obj = new FineArtsAppliedSkillsCodeEntity();
		obj.setFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		obj.setDescription("FineArtsAppliedSkillsCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(fineArtsAppliedSkillsCodeRepository.findById("fineArtsAppliedSkillsCode")).thenReturn(Optional.of(obj));
		var result = fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		assertNotNull(result);
	}
	
	@Test
	public void testGetFineArtsAppliedSkillsCode_noContent() {
		Mockito.when(fineArtsAppliedSkillsCodeRepository.findById("fineArtsAppliedSkillsCode")).thenReturn(Optional.empty());
		var result = fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		assertNotNull(result);
	}
}
