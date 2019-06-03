DROP SEQUENCE users_seq;
DROP TABLE ers_reimbursement;
DROP TABLE ers_users;
DROP TABLE ers_reimbursement_status;
DROP TABLE ers_reimbursement_type;
DROP TABLE ers_user_roles;

CREATE TABLE ers_user_roles (

    ers_user_role_id    NUMBER,
    user_role           VARCHAR2(10),
    
    CONSTRAINT ers_user_roles_pk PRIMARY KEY (ers_user_role_id)
);

CREATE TABLE ers_reimbursement_type (

    reimb_type_id       NUMBER,
    reimb_type          VARCHAR2(10),
    
    CONSTRAINT reimb_type_pk PRIMARY KEY (reimb_type_id)
);

CREATE TABLE ers_reimbursement_status (

    reimb_status_id     NUMBER,
    reimb_status        VARCHAR2(10),

    CONSTRAINT reimb_status_pk PRIMARY KEY (reimb_status_id)
);

CREATE TABLE ers_users (

    ers_users_id        NUMBER,
    ers_username        VARCHAR2(50) UNIQUE,
    ers_password        VARCHAR2(50),
    user_first_name     VARCHAR2(100),
    user_last_name      VARCHAR2(100),
    user_email          VARCHAR2(150) UNIQUE,
    user_role_id        NUMBER,
    
    CONSTRAINT ers_users_pk PRIMARY KEY (ers_users_id),
    
    CONSTRAINT ers_roles_fk FOREIGN KEY (user_role_id)
    REFERENCES ers_user_roles (ers_user_role_id)
);

CREATE TABLE ers_reimbursement (

    reimb_id            NUMBER,
    reimb_amount        NUMBER,
    reimb_submitted     TIMESTAMP,
    reimb_resolved      TIMESTAMP,
    reimb_description   VARCHAR2(250),
    reimb_receipt       BLOB,
    reimb_author        NUMBER,
    reimb_resolver      NUMBER,
    reimb_status_id     NUMBER,
    reimb_type_id       NUMBER,
    
    CONSTRAINT ers_reimbursement_pk PRIMARY KEY (reimb_id),
    
    CONSTRAINT ers_users_fk_auth FOREIGN KEY (reimb_author)
    REFERENCES ers_users (ers_users_id),
    
    CONSTRAINT ers_users_fk_reslvr FOREIGN KEY (reimb_resolver)
    REFERENCES ers_users (ers_users_id),
    
    CONSTRAINT ers_reimbursement_status_fk FOREIGN KEY (reimb_status_id)
    REFERENCES ers_reimbursement_status (reimb_status_id),
    
    CONSTRAINT ers_reimbursement_type_fk FOREIGN KEY (reimb_type_id)
    REFERENCES ers_reimbursement_type (reimb_type_id)
);

INSERT INTO ers_user_roles VALUES (1, 'employee');
INSERT INTO ers_user_roles VALUES (2, 'admin');

INSERT INTO ers_reimbursement_type VALUES (1, 'Lodging');
INSERT INTO ers_reimbursement_type VALUES (2, 'Travel');
INSERT INTO ers_reimbursement_type VALUES (3, 'Food');
INSERT INTO ers_reimbursement_type VALUES (4, 'Other');

INSERT INTO ers_reimbursement_status VALUES (1, 'Pending');
INSERT INTO ers_reimbursement_status VALUES (2, 'Approved');
INSERT INTO ers_reimbursement_status VALUES (3, 'Denied');

CREATE SEQUENCE users_seq;

CREATE OR REPLACE PROCEDURE new_user
(
username VARCHAR2,
password VARCHAR2,
firstname VARCHAR2,
lastname VARCHAR2,
email VARCHAR2
)
IS
BEGIN
    INSERT INTO ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES (users_seq.nextval, username, password, firstname, lastname, email, 1);
    COMMIT;
END;

INSERT INTO ers_users VALUES (1, 'Kerr007', 'benis69', 'Justin', 'Kerr', 'justin-kerr89@hotmail.com', 2);
SELECT * FROM ers_users;
SELECT * FROM ers_user_roles;
SELECT * FROM ers_reimbursement_status;