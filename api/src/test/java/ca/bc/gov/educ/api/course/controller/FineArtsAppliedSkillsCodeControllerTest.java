package ca.bc.gov.educ.api.course.controller;

import ca.bc.gov.educ.api.course.model.dto.FineArtsAppliedSkillsCode;
import ca.bc.gov.educ.api.course.service.FineArtsAppliedSkillsCodeService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class FineArtsAppliedSkillsCodeControllerTest {

	@Mock
	private FineArtsAppliedSkillsCodeService fineArtsAppliedSkillsCodeService;
	
	@InjectMocks
	private FineArtsAppliedSkillsCodeController fineArtsAppliedSkillsCodeController;
	
	@Test
	public void testGetFineArtsAppliedSkillsCodeList() {
		List<FineArtsAppliedSkillsCode> fineArtsAppliedSkillsCodes = new ArrayList<>();
		FineArtsAppliedSkillsCode obj = new FineArtsAppliedSkillsCode();
		obj.setFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		obj.setDescription("FineArtsAppliedSkillsCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		fineArtsAppliedSkillsCodes.add(obj);
		Mockito.when(fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCodeList()).thenReturn(fineArtsAppliedSkillsCodes);
		fineArtsAppliedSkillsCodeController.getFineArtsAppliedSkillsCodes();
		Mockito.verify(fineArtsAppliedSkillsCodeService).getFineArtsAppliedSkillsCodeList();

	}
	
	@Test
	public void testGetFineArtsAppliedSkillsCode() {
		FineArtsAppliedSkillsCode obj = new FineArtsAppliedSkillsCode();
		obj.setFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		obj.setDescription("FineArtsAppliedSkillsCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode")).thenReturn(obj);
		fineArtsAppliedSkillsCodeController.getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		Mockito.verify(fineArtsAppliedSkillsCodeService).getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
	}
	
	@Test
	public void testGetFineArtsAppliedSkillsCode_noContent() {
		Mockito.when(fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode")).thenReturn(null);
		ResponseEntity<FineArtsAppliedSkillsCode> result = fineArtsAppliedSkillsCodeController.getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		Mockito.verify(fineArtsAppliedSkillsCodeService).getFineArtsAppliedSkillsCode("fineArtsAppliedSkillsCode");
		assertTrue(result.getStatusCode().is4xxClientError());
	}
}
