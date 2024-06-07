package ca.bc.gov.educ.api.course.model.transformer;

import ca.bc.gov.educ.api.course.model.dto.EquivalentOrChallengeCode;
import ca.bc.gov.educ.api.course.model.entity.EquivalentOrChallengeCodeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EquivalentOrChallengeCodeTransformer {

    @Autowired
    ModelMapper modelMapper;

    public EquivalentOrChallengeCode transformToDTO (EquivalentOrChallengeCodeEntity equivalentOrChallengeCode) {
        return modelMapper.map(equivalentOrChallengeCode, EquivalentOrChallengeCode.class);
    }

    public EquivalentOrChallengeCode transformToDTO ( Optional<EquivalentOrChallengeCodeEntity> equivalentOrChallengeCode ) {
        EquivalentOrChallengeCodeEntity cae = new EquivalentOrChallengeCodeEntity();

        if (equivalentOrChallengeCode.isPresent())
            cae = equivalentOrChallengeCode.get();

        return modelMapper.map(cae, EquivalentOrChallengeCode.class);
    }

	public List<EquivalentOrChallengeCode> transformToDTO (Iterable<EquivalentOrChallengeCodeEntity> EquivalentOrChallengeCodeEntities ) {

        List<EquivalentOrChallengeCode> courseReqList = new ArrayList<>();

        for (EquivalentOrChallengeCodeEntity EquivalentOrChallengeCodeEntity : EquivalentOrChallengeCodeEntities) {
            EquivalentOrChallengeCode equivalentOrChallengeCode = modelMapper.map(EquivalentOrChallengeCodeEntity, EquivalentOrChallengeCode.class);
            courseReqList.add(equivalentOrChallengeCode);
        }

        return courseReqList;
    }

    public EquivalentOrChallengeCodeEntity transformToEntity(EquivalentOrChallengeCode equivalentOrChallengeCode) {
        return modelMapper.map(equivalentOrChallengeCode, EquivalentOrChallengeCodeEntity.class);
    }
}
