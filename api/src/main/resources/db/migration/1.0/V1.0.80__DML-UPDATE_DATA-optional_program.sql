-- SCCP
UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'SCCP French Language Certificate',
    DESCRIPTION = 'Students enrolled in Programme francophone who are reported on SCCP.'
WHERE GRADUATION_PROGRAM_CODE = 'SCCP'
AND OPTIONAL_PROGRAM_CODE = 'FR';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students on SCCP who are also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = 'SCCP'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

-- 2023
UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program who are also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '2023-PF'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program who are also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '2023-PF'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'English Dogwood',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program, who are also working towards the B.C. Certificate of Graduation (Dual Dogwood).'
WHERE GRADUATION_PROGRAM_CODE = '2023-PF'
  AND OPTIONAL_PROGRAM_CODE = 'DD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program who are also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2023-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d '' études secondaires en Colombie-Britannique program who are also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2023-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '2023-EN'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2023-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2023-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '2023-EN'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'French Immersion',
    DESCRIPTION = 'French Immersion students on the current B.C. Graduation program who are also working towards a Diplôme de fin d’études secondaires en Colombie-Britannique.'
WHERE GRADUATION_PROGRAM_CODE = '2023-EN'
  AND OPTIONAL_PROGRAM_CODE = 'FI';

-- 2018
UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program who are also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2018-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'English Dogwood',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program, who are also working towards the B.C. Certificate of Graduation (Dual Dogwood).'
WHERE GRADUATION_PROGRAM_CODE = '2018-PF'
  AND OPTIONAL_PROGRAM_CODE = 'DD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program who are also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '2018-PF'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program who are also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '2018-PF'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the current Diplôme de fin d''études secondaires en Colombie-Britannique program who are also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2018-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '2018-EN'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2018-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'French Immersion',
    DESCRIPTION = 'French Immersion students on the current B.C. Graduation program who are also working towards a Diplôme de fin d’études secondaires en Colombie-Britannique.'
WHERE GRADUATION_PROGRAM_CODE = '2018-EN'
  AND OPTIONAL_PROGRAM_CODE = 'FI';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '2018-EN'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students on the current B.C. Graduation program who are also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2018-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

-- 2004
UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 2004 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2004-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'English Dogwood',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 2004 Diplôme de fin d''études secondaires en Colombie-Britannique program, who were also working towards the B.C. Certificate of Graduation (Dual Dogwood).'
WHERE GRADUATION_PROGRAM_CODE = '2004-PF'
  AND OPTIONAL_PROGRAM_CODE = 'DD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 2004 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '2004-PF'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 2004 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '2004-PF'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 2004 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2004-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students on the 2004 B.C. Graduation program who were also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '2004-EN'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students on the 2004 B.C. Graduation program who were also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2004-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students on the 2004 B.C. Graduation program who were also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '2004-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'French Immersion',
    DESCRIPTION = 'French Immersion students on the 2004 B.C. Graduation program who were also working towards a Diplôme de fin d’études secondaires en Colombie-Britannique.'
WHERE GRADUATION_PROGRAM_CODE = '2004-EN'
  AND OPTIONAL_PROGRAM_CODE = 'FI';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students on the 2004 B.C. Graduation program who were also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '2004-EN'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

-- 1996
UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 1996 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '1996-PF'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 1996 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '1996-PF'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 1996 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1996-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 1996 Diplôme de fin d''études secondaires en Colombie-Britannique program who were also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1996-PF'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'English Dual Dogwood',
    DESCRIPTION = 'Students enrolled in Programme francophone on the 1996 Diplôme de fin d''études secondaires en Colombie-Britannique program, who were also working towards the B.C. Certificate of Graduation (Dual Dogwood).'
WHERE GRADUATION_PROGRAM_CODE = '1996-PF'
  AND OPTIONAL_PROGRAM_CODE = 'DD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'French Immersion',
    DESCRIPTION = 'French Immersion students on the 1996 B.C. Graduation program who were also working towards a Diplôme de fin d’études secondaires en Colombie-Britannique.'
WHERE GRADUATION_PROGRAM_CODE = '1996-EN'
  AND OPTIONAL_PROGRAM_CODE = 'FI';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students on the 1996 B.C. Graduation program who were also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '1996-EN'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students on the 1996 B.C. Graduation program who were also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1996-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students on the 1996 B.C. Graduation program who were also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1996-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students on the 1996 B.C. Graduation program who were also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '1996-EN'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

-- 1986
UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students on the 1986 B.C. Graduation program who were also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1986-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students on the 1986 B.C. Graduation program who were also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1986-EN'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students on the 1986 B.C. Graduation program who were also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '1986-EN'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'French Immersion',
    DESCRIPTION = 'French Immersion students on the 1986 B.C. Graduation program who were also working towards a Diplôme de fin d’études secondaires en Colombie-Britannique.'
WHERE GRADUATION_PROGRAM_CODE = '1986-EN'
  AND OPTIONAL_PROGRAM_CODE = 'FI';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Career Program',
    DESCRIPTION = 'Students on the 1986 B.C. Graduation program who were also reported as participating in a Career program.'
WHERE GRADUATION_PROGRAM_CODE = '1986-EN'
  AND OPTIONAL_PROGRAM_CODE = 'CP';

-- 1950
UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'Advanced Placement',
    DESCRIPTION = 'Students on the B.C. Adult Diploma program who are also reported as participating in the Advanced Placement program.'
WHERE GRADUATION_PROGRAM_CODE = '1950'
  AND OPTIONAL_PROGRAM_CODE = 'AD';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Certificate candidate',
    DESCRIPTION = 'Students on the B.C. Adult Diploma program who are also reported as participating in the International Baccalaureate program as a Certificate candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1950'
  AND OPTIONAL_PROGRAM_CODE = 'BC';

UPDATE OPTIONAL_PROGRAM SET
    LABEL = 'International Baccalaureate as a Diploma candidate',
    DESCRIPTION = 'Students on the B.C. Adult Diploma program who are also reported as participating in the International Baccalaureate program as a Diploma candidate.'
WHERE GRADUATION_PROGRAM_CODE = '1950'
  AND OPTIONAL_PROGRAM_CODE = 'BD';

