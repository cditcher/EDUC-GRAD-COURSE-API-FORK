package ca.bc.gov.educ.api.course.controller;

import ca.bc.gov.educ.api.course.model.dto.FineArtsAppliedSkillsCode;
import ca.bc.gov.educ.api.course.service.FineArtsAppliedSkillsCodeService;
import ca.bc.gov.educ.api.course.util.EducCourseApiConstants;
import ca.bc.gov.educ.api.course.util.PermissionsConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(EducCourseApiConstants.GRAD_COURSE_API_ROOT_MAPPING)
@OpenAPIDefinition(info = @Info(title = "API for Student Course Data.", description = "This API is for Reading Student Course data.", version = "1"),
        security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_COURSE_DATA"})})
public class FineArtsAppliedSkillsCodeController {

    @Autowired
    FineArtsAppliedSkillsCodeService fineArtsAppliedSkillsCodeService;

    @GetMapping(EducCourseApiConstants.FINE_ART_APPLIED_SKILLS_CODES_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_FINE_ART_APPLIED_SKILLS_CODE)
    @Operation(summary = "Find All Fine Arts Applied Skills Codes", description = "Find All Fine Arts Applied Skills Codes", tags = {"Fine Arts Applied Skills Codes"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<FineArtsAppliedSkillsCode>> getFineArtsAppliedSkillsCodes() {
        return ResponseEntity.ok().body(fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCodeList());
    }

    @GetMapping(EducCourseApiConstants.FINE_ART_APPLIED_SKILLS_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_FINE_ART_APPLIED_SKILLS_CODE)
    @Operation(summary = "Find Fine Arts Applied Skills Code", description = "Find Fine Arts Applied Skills Code", tags = {"Fine Arts Applied Skills Code"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "404", description = "NOT FOUND")})
    public ResponseEntity<FineArtsAppliedSkillsCode> getFineArtsAppliedSkillsCode(@PathVariable String fineArtsAppliedSkillsCode) {
        return ResponseEntity.ok().body(fineArtsAppliedSkillsCodeService.getFineArtsAppliedSkillsCode(fineArtsAppliedSkillsCode));
    }
}
