ALTER TABLE OPTIONAL_PROGRAM_CODE ADD ASSOCIATED_CREDENTIALS VARCHAR2(250);
UPDATE OPTIONAL_PROGRAM_CODE SET DESCRIPTION='French Immersion students on the current B.C. Graduation program who are also working towards a Diplôme de fin d’études secondaires en Colombie-Britannique.', ASSOCIATED_CREDENTIALS = 'Diplôme de fin d’études secondaires en Colombie-Britannique' WHERE OPTIONAL_PROGRAM_CODE='FI';
UPDATE OPTIONAL_PROGRAM_CODE SET DESCRIPTION='Students enrolled in Programme francophone who are working toward the Diplôme de fin d''études secondaires en Colombie-Britannique program, as well as the B.C. Certificate of Graduation (Dual Dogwood).', ASSOCIATED_CREDENTIALS = 'British Columbia Certificate of Graduation' WHERE OPTIONAL_PROGRAM_CODE='DD';
