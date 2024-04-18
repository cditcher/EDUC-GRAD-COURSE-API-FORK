-- FINE_ARTS_APPLIED_SKILLS_CODE definition

CREATE TABLE "FINE_ARTS_APPLIED_SKILLS_CODE"
(	"FINE_ARTS_APPLIED_SKILLS_CODE" VARCHAR2(1),
     "LABEL" VARCHAR2(50) NOT NULL ENABLE,
     "DESCRIPTION" VARCHAR2(355) NOT NULL ENABLE,
     "DISPLAY_ORDER" NUMBER NOT NULL ENABLE,
     "EFFECTIVE_DATE" DATE NOT NULL ENABLE,
     "EXPIRY_DATE" DATE,
     "CREATE_USER" VARCHAR2(32) DEFAULT USER NOT NULL ENABLE,
     "CREATE_DATE" DATE DEFAULT SYSTIMESTAMP NOT NULL ENABLE,
     "UPDATE_USER" VARCHAR2(32) DEFAULT USER NOT NULL ENABLE,
     "UPDATE_DATE" DATE DEFAULT SYSTIMESTAMP NOT NULL ENABLE,
     CONSTRAINT "FINE_ARTS_APPLIED_SKILLS_CODE_PK" PRIMARY KEY ("FINE_ARTS_APPLIED_SKILLS_CODE")
         USING INDEX TABLESPACE "API_GRAD_IDX"  ENABLE
) SEGMENT CREATION IMMEDIATE
 NOCOMPRESS LOGGING
  TABLESPACE "API_GRAD_DATA"   NO INMEMORY ;
  