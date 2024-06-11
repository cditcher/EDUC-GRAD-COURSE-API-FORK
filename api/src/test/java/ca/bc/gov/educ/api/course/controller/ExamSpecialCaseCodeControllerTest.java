package ca.bc.gov.educ.api.course.controller;

import ca.bc.gov.educ.api.course.model.dto.ExamSpecialCaseCode;
import ca.bc.gov.educ.api.course.service.ExamSpecialCaseCodeService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class ExamSpecialCaseCodeControllerTest {

	@Mock
	private ExamSpecialCaseCodeService examSpecialCaseCodeService;
	
	@InjectMocks
	private ExamSpecialCaseCodeController examSpecialCaseCodeController;
	
	@Test
	public void testGetExamSpecialCaseCodeList() {
		List<ExamSpecialCaseCode> examSpecialCaseCodes = new ArrayList<>();
		ExamSpecialCaseCode obj = new ExamSpecialCaseCode();
		obj.setExamSpecialCaseCode("examSpecialCaseCode");
		obj.setDescription("ExamSpecialCaseCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		examSpecialCaseCodes.add(obj);
		Mockito.when(examSpecialCaseCodeService.getExamSpecialCaseCodeList()).thenReturn(examSpecialCaseCodes);
		examSpecialCaseCodeController.getExamSpecialCaseCodes();
		Mockito.verify(examSpecialCaseCodeService).getExamSpecialCaseCodeList();

	}
	
	@Test
	public void testGetExamSpecialCaseCode() {
		ExamSpecialCaseCode obj = new ExamSpecialCaseCode();
		obj.setExamSpecialCaseCode("examSpecialCaseCode");
		obj.setDescription("ExamSpecialCaseCode Description");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(examSpecialCaseCodeService.getExamSpecialCaseCode("examSpecialCaseCode")).thenReturn(obj);
		examSpecialCaseCodeController.getExamSpecialCaseCode("examSpecialCaseCode");
		Mockito.verify(examSpecialCaseCodeService).getExamSpecialCaseCode("examSpecialCaseCode");
	}
	
	@Test
	public void testGetExamSpecialCaseCode_noContent() {
		Mockito.when(examSpecialCaseCodeService.getExamSpecialCaseCode("examSpecialCaseCode")).thenReturn(null);
		examSpecialCaseCodeController.getExamSpecialCaseCode("examSpecialCaseCode");
		Mockito.verify(examSpecialCaseCodeService).getExamSpecialCaseCode("examSpecialCaseCode");
	}
}
