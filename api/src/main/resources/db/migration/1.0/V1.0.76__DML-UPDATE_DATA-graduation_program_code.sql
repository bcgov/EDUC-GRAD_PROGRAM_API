UPDATE GRADUATION_PROGRAM_CODE SET
    LABEL = 'Graduation Program 2023',
    ASSOCIATED_CREDENTIAL = 'British Columbia Certificate of Graduation',
    EFFECTIVE_DATE = TIMESTAMP'2023-07-01 00:00:00.0',
    EXPIRY_DATE = NULL,
    DISPLAY_ORDER = 40,
    DESCRIPTION = 'The 2023 Diplôme de fin d''études secondaires en Colombie-Britannique program for students enrolled in Programme francophone.'
    WHERE GRADUATION_PROGRAM_CODE='2023-PF';

