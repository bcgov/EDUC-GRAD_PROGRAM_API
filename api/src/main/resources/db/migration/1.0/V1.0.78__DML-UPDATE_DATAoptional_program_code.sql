UPDATE OPTIONAL_PROGRAM_CODE SET
    LABEL = 'SCCP French Language Certificate',
    DESCRIPTION = 'Students enrolled in Programme francophone who are reported on SCCP.',
    ASSOCIATED_CREDENTIALS = 'Certificat de fin d''Études'
    WHERE OPTIONAL_PROGRAM_CODE = 'FR';

UPDATE OPTIONAL_PROGRAM_CODE SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'An enriched secondary school program that provides students with the opportunity to earn advanced credit towards their undergraduate degrees. Students must be reported by the school as participating in this program.',
    ASSOCIATED_CREDENTIALS = 'Notation of participation on transcript'
    WHERE OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM_CODE SET
    LABEL = 'English Dogwood',
    DESCRIPTION = 'Students enrolled in Programme francophone who are also working towards a B.C. Certificate of Graduation.',
    ASSOCIATED_CREDENTIALS = 'British Columbia Certificate of Graduation'
    WHERE OPTIONAL_PROGRAM_CODE = 'DD';

UPDATE OPTIONAL_PROGRAM_CODE SET
    LABEL = 'French Immersion',
    DESCRIPTION = 'A program for French Immersion students on B.C. Graduation program who are also working towards a Diplôme de fin d’études secondaires en Colombie-Britannique.',
    ASSOCIATED_CREDENTIALS = 'Diplôme de fin d’études secondaires en Colombie-Britannique'
    WHERE OPTIONAL_PROGRAM_CODE = 'FI';

UPDATE OPTIONAL_PROGRAM_CODE SET
    LABEL = 'Career Program',
    DESCRIPTION = 'A program that provides students with the opportunity to complete eligible work experiences or trades training courses. Students must be reported by the school as participating in a Career Program and complete an eligible experience or course.',
    ASSOCIATED_CREDENTIALS = 'Notation of completion on transcript'
    WHERE OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM_CODE SET
    LABEL = 'International Baccalaureate Certificate',
    DESCRIPTION = 'The International Baccalaureate Certificate program. Students must be reported by the school as participating in this program.',
    ASSOCIATED_CREDENTIALS = 'Notation of participation on transcript'
    WHERE OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM_CODE SET
    LABEL = 'International Baccalaureate Diploma',
    DESCRIPTION = 'The International Baccalaureate Diploma program. Students must be reported by the school as participating in this program.',
    ASSOCIATED_CREDENTIALS = 'Notation of participation on transcript'
    WHERE OPTIONAL_PROGRAM_CODE = 'BD';

