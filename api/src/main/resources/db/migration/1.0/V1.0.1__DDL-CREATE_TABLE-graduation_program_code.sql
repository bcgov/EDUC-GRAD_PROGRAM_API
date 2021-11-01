-- GRADUATION_PROGRAM_CODE definition

CREATE TABLE "GRADUATION_PROGRAM_CODE" 
   (	"GRADUATION_PROGRAM_CODE" VARCHAR2(8), 
	"LABEL" VARCHAR2(50) NOT NULL ENABLE, 
	"DESCRIPTION" VARCHAR2(355) NOT NULL ENABLE,
	"DISPLAY_ORDER" NUMBER NOT NULL ENABLE, 
	"EFFECTIVE_DATE" DATE NOT NULL ENABLE, 
	"EXPIRY_DATE" DATE, 
	"CREATE_USER" VARCHAR2(32) DEFAULT USER NOT NULL ENABLE, 
	"CREATE_DATE" DATE DEFAULT SYSTIMESTAMP NOT NULL ENABLE, 
	"UPDATE_USER" VARCHAR2(32) DEFAULT USER NOT NULL ENABLE, 
	"UPDATE_DATE" DATE DEFAULT SYSTIMESTAMP NOT NULL ENABLE, 
	 CONSTRAINT "GRAD_PROGRAM_CODE_PK" PRIMARY KEY ("GRADUATION_PROGRAM_CODE")
  USING INDEX TABLESPACE "API_GRAD_IDX"  ENABLE
   ) SEGMENT CREATION IMMEDIATE 
 NOCOMPRESS LOGGING
  TABLESPACE "API_GRAD_DATA"   NO INMEMORY ;
