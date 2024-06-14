package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.ExamSpecialCaseCode;
import ca.bc.gov.educ.api.course.model.entity.ExamSpecialCaseCodeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ExamSpecialCaseCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public ExamSpecialCaseCode transformToDTO (ExamSpecialCaseCodeEntity examSpecialCaseCodeEntity) {
        return modelMapper.map(examSpecialCaseCodeEntity, ExamSpecialCaseCode.class);
    }

    public ExamSpecialCaseCode transformToDTO ( Optional<ExamSpecialCaseCodeEntity> examSpecialCaseCodeEntity ) {
        if (examSpecialCaseCodeEntity.isPresent()) {
            ExamSpecialCaseCodeEntity cae = examSpecialCaseCodeEntity.get();
            return modelMapper.map(cae, ExamSpecialCaseCode.class);
        }
        return null;
    }

	public List<ExamSpecialCaseCode> transformToDTO (Iterable<ExamSpecialCaseCodeEntity> ExamSpecialCaseCodeEntities ) {

        List<ExamSpecialCaseCode> courseReqList = new ArrayList<>();

        for (ExamSpecialCaseCodeEntity ExamSpecialCaseCodeEntity : ExamSpecialCaseCodeEntities) {
            ExamSpecialCaseCode examSpecialCaseCode = modelMapper.map(ExamSpecialCaseCodeEntity, ExamSpecialCaseCode.class);
            courseReqList.add(examSpecialCaseCode);
        }

        return courseReqList;
    }

    public ExamSpecialCaseCodeEntity transformToEntity(ExamSpecialCaseCode examSpecialCaseCode) {
        return modelMapper.map(examSpecialCaseCode, ExamSpecialCaseCodeEntity.class);
    }
}
