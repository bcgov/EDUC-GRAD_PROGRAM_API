UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Adult Graduation Program',
    ASSOCIATED_CREDENTIAL = 'British Columbia Adult Graduation Diploma',
    EFFECTIVE_DATE = TIMESTAMP'1999-09-01 00:00:00.0',
    EXPIRY_DATE = NULL,
    DESCRIPTION = 'The B.C. Adult Graduation Diploma program, also known as the "Adult Dogwood".'
    WHERE GRADUATION_PROGRAM_CODE='1950';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Course Based Graduation Program',
    ASSOCIATED_CREDENTIAL = 'British Columbia Certificate of Graduation',
    EFFECTIVE_DATE = TIMESTAMP'1985-07-01 00:00:00.0',
    EXPIRY_DATE = TIMESTAMP'2001-06-30 00:00:00.0',
    DESCRIPTION = 'The 1986 B.C. Certificate of Graduation or "Dogwood Diploma" program.'
    WHERE GRADUATION_PROGRAM_CODE='1986-EN';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 1995',
    ASSOCIATED_CREDENTIAL = 'British Columbia Certificate of Graduation',
    EFFECTIVE_DATE = TIMESTAMP'1996-07-01 00:00:00.0',
    EXPIRY_DATE = TIMESTAMP'2011-06-30 00:00:00.0',
    DESCRIPTION = 'The 1995 B.C. Certificate of Graduation or "Dogwood Diploma" program.'
    WHERE GRADUATION_PROGRAM_CODE='1996-EN';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 1995',
    ASSOCIATED_CREDENTIAL = 'Diplôme de fin d''études secondaires en Colombie-Britannique',
    EFFECTIVE_DATE = TIMESTAMP'1996-09-01 00:00:00.0',
    EXPIRY_DATE = TIMESTAMP'2011-06-30 00:00:00.0',
    DESCRIPTION = 'The 1995 Diplôme de fin d''études secondaires en Colombie-Britannique for students enrolled in Programme francophone.'
    WHERE GRADUATION_PROGRAM_CODE='1996-PF';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 2004',
    ASSOCIATED_CREDENTIAL = 'British Columbia Certificate of Graduation',
    EFFECTIVE_DATE = TIMESTAMP'2001-07-01 00:00:00.0',
    EXPIRY_DATE = TIMESTAMP'2021-06-30 00:00:00.0',
    DESCRIPTION = 'The 2004 B.C. Certificate of Graduation or "Dogwood Diploma" program.'
    WHERE GRADUATION_PROGRAM_CODE='2004-EN';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 2004',
    ASSOCIATED_CREDENTIAL = 'Diplôme de fin d''études secondaires en Colombie-Britannique',
    EFFECTIVE_DATE = TIMESTAMP'2001-07-01 00:00:00.0',
    EXPIRY_DATE = TIMESTAMP'2021-06-30 00:00:00.0',
    DESCRIPTION = 'The 2004 Diplôme de fin d''études secondaires en Colombie-Britannique for students enrolled in Programme francophone.'
    WHERE GRADUATION_PROGRAM_CODE='2004-PF';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 2018',
    ASSOCIATED_CREDENTIAL = 'British Columbia Certificate of Graduation',
    EFFECTIVE_DATE = TIMESTAMP'2018-07-01 00:00:00.0',
    EXPIRY_DATE = NULL,
    DESCRIPTION = 'The 2018 B.C. Certificate of Graduation or "Dogwood Diploma" program. '
    WHERE GRADUATION_PROGRAM_CODE='2018-EN';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 2018',
    ASSOCIATED_CREDENTIAL = 'Diplôme de fin d''études secondaires en Colombie-Britannique',
    EFFECTIVE_DATE = TIMESTAMP'2018-07-01 00:00:00.0',
    EXPIRY_DATE = NULL,
    DESCRIPTION = 'The 2018 Diplôme de fin d''études secondaires en Colombie-Britannique program for students enrolled in Programme francophone.'
    WHERE GRADUATION_PROGRAM_CODE='2018-PF';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 2023',
    ASSOCIATED_CREDENTIAL = 'British Columbia Certificate of Graduation',
    EFFECTIVE_DATE = TIMESTAMP'2023-07-01 00:00:00.0',
    EXPIRY_DATE = NULL,
    DESCRIPTION = 'The 2023 B.C. Certificate of Graduation or “Dogwood Diploma” program.'
    WHERE GRADUATION_PROGRAM_CODE='2023-EN';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 2023',
    ASSOCIATED_CREDENTIAL = 'The 2023 Diplôme de fin d''études secondaires en Colombie-Britannique program for students enrolled in Programme francophone. ',
    EFFECTIVE_DATE = TIMESTAMP'2023-07-01 00:00:00.0',
    EXPIRY_DATE = NULL,
    DESCRIPTION = 'The 2023 Diplôme de fin d''études secondaires en Colombie-Britannique program for students enrolled in Programme francophone. '
    WHERE GRADUATION_PROGRAM_CODE='2023-PF';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'No Program Specified',
    ASSOCIATED_CREDENTIAL = NULL,
    EFFECTIVE_DATE = TIMESTAMP'2021-06-14 00:00:00.0',
    EXPIRY_DATE = NULL,
    DESCRIPTION = 'Students not on a recognized GRAD system program but completing grades 10-12 courses (e.g., pre-1986 and out-of-jurisdiction graduates, or international students not on a B.C. graduation program).'
    WHERE GRADUATION_PROGRAM_CODE='NOPROG';
UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Student Completion Certificate Program',
    ASSOCIATED_CREDENTIAL = 'British Columbia School Completion Certificate',
    EFFECTIVE_DATE = TIMESTAMP'2006-07-01 00:00:00.0',
    EXPIRY_DATE = NULL,
    DESCRIPTION = 'The School Completion Certificate Program (SCCP) recognizes the accomplishments of students with special needs and an Individual Education Plan who have met the goals of their education program. It is not a graduation credential.'
    WHERE GRADUATION_PROGRAM_CODE='SCCP';

