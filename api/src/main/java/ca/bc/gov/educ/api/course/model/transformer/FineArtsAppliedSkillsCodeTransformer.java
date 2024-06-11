package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.FineArtsAppliedSkillsCode;
import ca.bc.gov.educ.api.course.model.entity.FineArtsAppliedSkillsCodeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FineArtsAppliedSkillsCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public FineArtsAppliedSkillsCode transformToDTO (FineArtsAppliedSkillsCodeEntity fineArtsAppliedSkillsCode) {
        return modelMapper.map(fineArtsAppliedSkillsCode, FineArtsAppliedSkillsCode.class);
    }

    public FineArtsAppliedSkillsCode transformToDTO ( Optional<FineArtsAppliedSkillsCodeEntity> fineArtsAppliedSkillsCode ) {
        FineArtsAppliedSkillsCodeEntity cae = new FineArtsAppliedSkillsCodeEntity();

        if (fineArtsAppliedSkillsCode.isPresent())
            cae = fineArtsAppliedSkillsCode.get();

        return modelMapper.map(cae, FineArtsAppliedSkillsCode.class);
    }

	public List<FineArtsAppliedSkillsCode> transformToDTO (Iterable<FineArtsAppliedSkillsCodeEntity> fineArtsAppliedSkillsCodes ) {

        List<FineArtsAppliedSkillsCode> courseReqList = new ArrayList<>();

        for (FineArtsAppliedSkillsCodeEntity fineArtsAppliedSkillsCode : fineArtsAppliedSkillsCodes) {
            FineArtsAppliedSkillsCode fineArtsAppliedSkill = modelMapper.map(fineArtsAppliedSkillsCode, FineArtsAppliedSkillsCode.class);
            courseReqList.add(fineArtsAppliedSkill);
        }

        return courseReqList;
    }

    public FineArtsAppliedSkillsCodeEntity transformToEntity(FineArtsAppliedSkillsCode FineArtsAppliedSkills) {
        return modelMapper.map(FineArtsAppliedSkills, FineArtsAppliedSkillsCodeEntity.class);
    }
}
