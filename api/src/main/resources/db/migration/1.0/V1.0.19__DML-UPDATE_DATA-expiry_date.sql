UPDATE GRADUATION_PROGRAM_CODE arc SET arc.EXPIRY_DATE = NULL WHERE arc.EXPIRY_DATE = TO_DATE('2099-12-31', 'yyyy-mm-dd');
UPDATE OPTIONAL_PROGRAM arc SET arc.EXPIRY_DATE = NULL WHERE arc.EXPIRY_DATE = TO_DATE('2099-12-31', 'yyyy-mm-dd');
UPDATE OPTIONAL_PROGRAM_CODE arc SET arc.EXPIRY_DATE = NULL WHERE arc.EXPIRY_DATE = TO_DATE('2099-12-31', 'yyyy-mm-dd');
UPDATE REQUIREMENT_TYPE_CODE arc SET arc.EXPIRY_DATE = NULL WHERE arc.EXPIRY_DATE = TO_DATE('2099-12-31', 'yyyy-mm-dd');