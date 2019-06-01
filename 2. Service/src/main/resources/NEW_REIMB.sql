CREATE OR REPLACE PROCEDURE new_reimb(p_AUTHOR IN VARCHAR2,
                                      p_AMOUNT IN number,
                                      p_DESCRIPTION IN VARCHAR2,
                                      p_TYPE number)
    IS

    --DECLARE VARIABLES
    v_AUTHOR    ERS_USERS.ERS_USERNAME%TYPE;
    --Here we declare %TYPE in case
    v_AUTHOR_ID ERS_USERS.ERS_USERS_ID%TYPE;

BEGIN

    --SET VARIABLES FROM PARAMETERS
    v_AUTHOR := UPPER(p_AUTHOR);
    --Converting to uppercase to
    --ignore case sensitivity

    --GET THE AUTHOR_ID
    SELECT ERS_USERS_ID INTO v_AUTHOR_ID
    FROM ERS_USERS
    WHERE UPPER(ERS_USERNAME) = v_AUTHOR;
    --Converting to uppercase
    --to ignore case sensitivity

    INSERT INTO ERS_REIMBURSEMENT
    (REIMB_ID,
     REIMB_AMOUNT,
     REIMB_SUBMITTED,
     REIMB_RESOLVED,
     REIMB_DESCRIPTION,
     REIMB_RECEIPT,
     REIMB_AUTHOR,
     REIMB_RESOLVER,
     REIMB_STATUS_ID,
     REIMB_TYPE_ID)
    VALUES (reimb_seq.nextval, -- The next id in the table
            p_AMOUNT, --Amount passed in when this is called
            systimestamp, --grabs local db time
            null, --New reimbs aren't resolved on creation
            p_DESCRIPTION, --Description passed
            null, --Will have to figure this out later.
            v_AUTHOR_ID, -- The authorID grabbed earlier in this procedure
            null, --If it's not resolved there can be no resolver
            1, -- All reimbs are automatically pending
            p_TYPE --Specifies what type of expense it is.

           );

    --COMMIT OPEN TRANSACTIONS
    COMMIT;
END;
/