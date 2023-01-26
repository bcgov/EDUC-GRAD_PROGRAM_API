UPDATE PROGRAM_REQUIREMENT_CODE
SET LABEL='Language Arts 12',
    DESCRIPTION='Students must complete a 4-credit course from Language Arts 12 subject area',
    NOT_MET_DESC='No Language Arts 12'
WHERE PROGRAM_REQUIREMENT_CODE='500';

UPDATE PROGRAM_REQUIREMENT_CODE
SET LABEL='Mathematics 11 or 12',
    DESCRIPTION='Students must complete a 4-credit course from Mathematics 11 or 12 subject area',
    NOT_MET_DESC='No Mathematics 11 or 12'
WHERE PROGRAM_REQUIREMENT_CODE='501';

UPDATE PROGRAM_REQUIREMENT_CODE
SET LABEL='Elective credits - Social Studies 11',
    DESCRIPTION='Students may use a 4-credit Social Studies 11 course to meet one of their Grade 12 elective requirements',
    NOT_MET_DESC='N/A'
WHERE PROGRAM_REQUIREMENT_CODE='502';

UPDATE PROGRAM_REQUIREMENT_CODE
SET LABEL='Electives - two Gr 12 level courses',
    DESCRIPTION='Students must complete a minimum of three eligible elective courses (Part 1)',
    NOT_MET_DESC='Fewer than three eligible elective courses'
WHERE PROGRAM_REQUIREMENT_CODE='503';

UPDATE PROGRAM_REQUIREMENT_CODE
SET LABEL='3 Courses completed after starting the program',
    DESCRIPTION='Students must complete three of five required courses after starting the program',
    NOT_MET_DESC='Fewer than 3 eligible courses completed after starting the program'
WHERE PROGRAM_REQUIREMENT_CODE='504';

UPDATE PROGRAM_REQUIREMENT_CODE
SET LABEL='Electives - one Gr 12 level or SS 11 course',
    DESCRIPTION='Students must complete a minimum of three eligible elective courses (Part 2)',
    NOT_MET_DESC='Fewer than three eligible elective courses'
WHERE PROGRAM_REQUIREMENT_CODE='505';

UPDATE PROGRAM_REQUIREMENT_CODE
SET LABEL='Work Experience Course',
    DESCRIPTION='Students expected to graduate prior to 201409 can only use one Work Experience Grade 12 course to meet a requirement',
    NOT_MET_DESC='N/A'
WHERE PROGRAM_REQUIREMENT_CODE='506';