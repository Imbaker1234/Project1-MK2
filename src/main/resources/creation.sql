create sequence USERS_SEQ
/

create sequence REIMB_SEQ
/

create table ERS_USER_ROLES
(
    ERS_USER_ROLE_ID NUMBER not null
        constraint ERS_USER_ROLES_PK
            primary key,
    USER_ROLE        VARCHAR2(10)
)
/

create table ERS_REIMBURSEMENT_TYPE
(
    REIMB_TYPE_ID NUMBER not null
        constraint REIMB_TYPE_PK
            primary key,
    REIMB_TYPE    VARCHAR2(10)
)
/

create table ERS_REIMBURSEMENT_STATUS
(
    REIMB_STATUS_ID NUMBER not null
        constraint REIMB_STATUS_PK
            primary key,
    REIMB_STATUS    VARCHAR2(10)
)
/

create table ERS_USERS
(
    ERS_USERS_ID    NUMBER not null
        constraint ERS_USERS_PK
            primary key,
    ERS_USERNAME    VARCHAR2(50)
        unique,
    ERS_PASSWORD    VARCHAR2(50),
    USER_FIRST_NAME VARCHAR2(100),
    USER_LAST_NAME  VARCHAR2(100),
    USER_EMAIL      VARCHAR2(150)
        unique,
    USER_ROLE_ID    NUMBER
        constraint ERS_ROLES_FK
            references ERS_USER_ROLES,
    ERS_SALT        VARCHAR2(600)
)
/

create table ERS_REIMBURSEMENT
(
    REIMB_ID          NUMBER not null
        constraint ERS_REIMBURSEMENT_PK
            primary key,
    REIMB_AMOUNT      NUMBER,
    REIMB_SUBMITTED   TIMESTAMP(6),
    REIMB_RESOLVED    TIMESTAMP(6),
    REIMB_DESCRIPTION VARCHAR2(250),
    REIMB_RECEIPT     BLOB,
    REIMB_AUTHOR      NUMBER
        constraint ERS_USERS_FK_AUTH
            references ERS_USERS,
    REIMB_RESOLVER    NUMBER
        constraint ERS_USERS_FK_RESLVR
            references ERS_USERS,
    REIMB_STATUS_ID   NUMBER
        constraint ERS_REIMBURSEMENT_STATUS_FK
            references ERS_REIMBURSEMENT_STATUS,
    REIMB_TYPE_ID     NUMBER
        constraint ERS_REIMBURSEMENT_TYPE_FK
            references ERS_REIMBURSEMENT_TYPE
)
/

create or replace PROCEDURE new_user(username VARCHAR2,
                          password VARCHAR2,
                          firstname VARCHAR2,
                          lastname VARCHAR2,
                          email VARCHAR2,
                          salt VARCHAR2)
    IS
BEGIN
    INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email,
                           user_role_id, ers_salt)
    VALUES (users_seq.nextval, username, password, firstname, lastname, email, 1, salt);
    COMMIT;
END;
/

create or replace PROCEDURE new_reimb(p_AUTHOR IN VARCHAR2,
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

create or replace PROCEDURE resolve_reimb(p_REIMB_ID in number,
                               p_RESOLVER in VARCHAR2,
                               p_STATUS_ID in number)
    IS

    --DECLARE VARIABLES
    v_RESOLVER    ERS_USERS.ERS_USERNAME%TYPE;
    --Here we declare %TYPE in case
    v_RESOLVER_ID ERS_USERS.ERS_USERS_ID%TYPE;

BEGIN

    --SET VARIABLES FROM PARAMETERS
    v_RESOLVER := UPPER(p_RESOLVER);
    --Converting to uppercase to
    --ignore case sensitivity

    --GET THE AUTHOR_ID
    SELECT ERS_USERS_ID INTO v_RESOLVER_ID
    FROM ERS_USERS
    WHERE UPPER(ERS_USERNAME) = v_RESOLVER;
    --Converting to uppercase
    --to ignore case sensitivity

    UPDATE ERS_REIMBURSEMENT
    SET REIMB_RESOLVED  = systimestamp,
        REIMB_RESOLVER  = v_RESOLVER_ID,
        REIMB_STATUS_ID = p_STATUS_ID
    WHERE REIMB_ID = p_REIMB_ID;

    --COMMIT OPEN TRANSACTIONS
    COMMIT;
END;
/

INSERT INTO ers_user_roles VALUES (1, 'employee');
INSERT INTO ers_user_roles VALUES (2, 'admin');

INSERT INTO ers_reimbursement_type VALUES (1, 'Lodging');
INSERT INTO ers_reimbursement_type VALUES (2, 'Travel');
INSERT INTO ers_reimbursement_type VALUES (3, 'Food');
INSERT INTO ers_reimbursement_type VALUES (4, 'Other');

INSERT INTO ers_reimbursement_status VALUES (1, 'Pending');
INSERT INTO ers_reimbursement_status VALUES (2, 'Approved');
INSERT INTO ers_reimbursement_status VALUES (3, 'Denied');

commit;