drop table "AWEB_AUTHORITY";

drop table "AWEB_FILE";

drop table "AWEB_MENU";

drop table "AWEB_ROLE";

drop table "AWEB_ROLE_AUTHORITY";

drop table "AWEB_ROLE_MENU";

drop table "AWEB_USER";

drop table "AWEB_USER_ROLE";

drop table "DEMO_EXCEL";

create table aweb_authority (
   ID                   VARCHAR(50)            not null,
   CREATE_TIME          VARCHAR(255),
   CREATE_USER_ID       VARCHAR(255),
   CREATE_USER_NAME     VARCHAR(255),
   UPDATE_TIME          VARCHAR(255),
   UPDATE_USER_ID       VARCHAR(255),
   UPDATE_USER_NAME     VARCHAR(255),
   DESCRIPTION          VARCHAR(255),
   NAME                 VARCHAR(255)           not null,
   PARENT_ID            VARCHAR(50),
   constraint PK_aweb_authority primary key (ID),
   constraint UK_f0cmfe8wqlt27hx76n0h9o2yf unique (NAME)
);

INSERT INTO AWEB_AUTHORITY VALUES ('auth-d2CHlFWd', '1577254488052', 'usr-admin', 'admin', '1577254488052', 'usr-admin', 'admin', '管理角色的权限', 'authorityManagement', null);
INSERT INTO AWEB_AUTHORITY VALUES ('auth-hg1Iu2lG', '1577254471036', 'usr-admin', 'admin', '1577254471036', 'usr-admin', 'admin', '管理角色的权限', 'roleManagement', null);
INSERT INTO AWEB_AUTHORITY VALUES ('auth-l4ZqCT4J', '1578452859460', 'usr-admin', 'admin', '1578452859460', 'usr-admin', 'admin', '管理菜单的权限', 'menuManagement', null);
INSERT INTO AWEB_AUTHORITY VALUES ('auth-OLVmaPOj', '1577254457368', 'usr-admin', 'admin', '1577254457368', 'usr-admin', 'admin', '管理用户的权限', 'userManagement', null);

--==============================================================
-- Table: "aweb_file"
--==============================================================
create table aweb_file (
   ID                   VARCHAR(50)            not null,
   GROUP_PATH           VARCHAR(255),
   NAME                 VARCHAR(255),
   NODE_PATH            VARCHAR(255),
   SIZE                 BIGINT,
   SUFFIX_NAME          VARCHAR(255),
   constraint PK_aweb_file primary key (ID)
);

--==============================================================
-- Table: "aweb_menu"
--==============================================================
create table aweb_menu (
   ID                   VARCHAR(50)            not null,
   CREATE_TIME          VARCHAR(255),
   CREATE_USER_ID       VARCHAR(255),
   CREATE_USER_NAME     VARCHAR(255),
   UPDATE_TIME          VARCHAR(255),
   UPDATE_USER_ID       VARCHAR(255),
   UPDATE_USER_NAME     VARCHAR(255),
   NAME                 VARCHAR(255)           not null,
   ORDER_               INTEGER,
   PARENT_ID            VARCHAR(50),
   TITLE                VARCHAR(50)            not null,
   DESCRIPTION          VARCHAR(255),
   ICON                 VARCHAR(50),
   PATH              VARCHAR(400),
   DEPLOY               NUMERIC(3, 0),
   constraint PK_aweb_menu primary key (ID),
   constraint UK_oyl0vtq40kyv8fdfjuade2yqb unique (NAME)
);

INSERT INTO AWEB_MENU VALUES ('file-362KK0Ra', '1578991960257', 'usr-admin', 'admin', '1581050117269', 'usr-admin', 'admin', 'userList', 2, 'file-RaEbwYZ3', '用户管理', '用户列表', 'el-icon-setting', 'user/userList',0.0);
INSERT INTO AWEB_MENU VALUES ('file-4DQOBJY9', '1580805691150', 'usr-admin', 'admin', '1581050111314', 'usr-admin', 'admin', 'menuList', 5, 'file-RaEbwYZ3', '菜单管理', '', 'el-icon-setting', 'menu/menuList',0.0);
INSERT INTO AWEB_MENU VALUES ('file-nuxpnaiU', '1580815008102', 'usr-admin', 'admin', '1581050080711', 'usr-admin', 'admin', 'authorityList', 4, 'file-RaEbwYZ3', '权限管理', '', 'el-icon-setting', 'authority/authorityList',0.0);
INSERT INTO AWEB_MENU VALUES ('file-PNmjz9PU', '1578991896698', 'usr-admin', 'admin', '1581050122226', 'usr-admin', 'admin', 'roleList', 3, 'file-RaEbwYZ3', '角色管理', '角色列表', 'el-icon-setting', 'role/roleList',0.0);
INSERT INTO AWEB_MENU VALUES ('file-RaEbwYZ3', '1581049773800', 'usr-admin', 'admin', '1581999128764', 'usr-admin', 'admin', 'userAuthorityManagement', 1, '', '用户权限管理', '', 'el-icon-user-solid', '',0.0);

--==============================================================
-- Table: "aweb_role"
--==============================================================
create table aweb_role (
   ID                   VARCHAR(50)            not null,
   CREATE_TIME          VARCHAR(255),
   CREATE_USER_ID       VARCHAR(255),
   CREATE_USER_NAME     VARCHAR(255),
   UPDATE_TIME          VARCHAR(255),
   UPDATE_USER_ID       VARCHAR(255),
   UPDATE_USER_NAME     VARCHAR(255),
   DESCRIPTION          VARCHAR(255),
   NAME                 VARCHAR(255)           not null,
   PERMISSIONS          VARCHAR(1024),
   constraint PK_aweb_role primary key (ID),
   constraint UK_1jgik2hgsmfm7g4ee2nf5m6h3 unique (NAME)
);

INSERT INTO AWEB_ROLE VALUES ('rol-QAv9DcTN', '1577254344223', 'usr-admin', 'admin', '1583227806273', 'usr-admin', 'admin', '管理员、拥有管理用户、角色、权限、菜单这四者的权限', 'ROLE_ADMIN','');

--==============================================================
-- Table: "aweb_role_authority"
--==============================================================
create table aweb_role_authority (
   ROLE_ID              VARCHAR(50)            not null,
   AUTHORITY_ID         VARCHAR(50)            not null
);

INSERT INTO AWEB_ROLE_AUTHORITY VALUES ('rol-QAv9DcTN', 'auth-d2CHlFWd');
INSERT INTO AWEB_ROLE_AUTHORITY VALUES ('rol-QAv9DcTN', 'auth-hg1Iu2lG');
INSERT INTO AWEB_ROLE_AUTHORITY VALUES ('rol-QAv9DcTN', 'auth-l4ZqCT4J');
INSERT INTO AWEB_ROLE_AUTHORITY VALUES ('rol-QAv9DcTN', 'auth-OLVmaPOj');

--==============================================================
-- Table: "aweb_role_menu"
--==============================================================
create table aweb_role_menu (
   ROLE_ID              VARCHAR(50)            not null,
   MENU_ID              VARCHAR(50)            not null
);

INSERT INTO AWEB_ROLE_MENU VALUES ('rol-QAv9DcTN', 'file-362KK0Ra');
INSERT INTO AWEB_ROLE_MENU VALUES ('rol-QAv9DcTN', 'file-PNmjz9PU');
INSERT INTO AWEB_ROLE_MENU VALUES ('rol-QAv9DcTN', 'file-nuxpnaiU');
INSERT INTO AWEB_ROLE_MENU VALUES ('rol-QAv9DcTN', 'file-4DQOBJY9');
INSERT INTO AWEB_ROLE_MENU VALUES ('rol-QAv9DcTN', 'file-RaEbwYZ3');

--==============================================================
-- Table: "aweb_user"
--==============================================================
create table aweb_user (
   ID                   VARCHAR(50)            not null,
   CREATE_TIME          VARCHAR(255),
   CREATE_USER_ID       VARCHAR(255),
   CREATE_USER_NAME     VARCHAR(255),
   UPDATE_TIME          VARCHAR(255),
   UPDATE_USER_ID       VARCHAR(255),
   UPDATE_USER_NAME     VARCHAR(255),
   DESCRIPTION          VARCHAR(255),
   EMAIL                VARCHAR(100),
   NAME                 VARCHAR(50)            not null,
   NICKNAME             VARCHAR(200),
   PASSWORD             VARCHAR(128)           not null,
   PHONE                VARCHAR(18),
   STATUS               VARCHAR(2),
   constraint PK_aweb_user primary key (ID),
   constraint UK_kpjtmcyew2jx0jn8ouw1jmw2n unique (NAME)
);

INSERT INTO AWEB_USER VALUES ('usr-admin', '', '', '', '1581050800138', 'usr-admin', 'admin', '', '', 'admin', '管理员', '$2a$10$852p2TT7sJjZEHE64liF.ObcvwIYTfJ4FYE4s1c4Z/.IuwWD89QDa', '', '1');

--==============================================================
-- Table: "aweb_user_role"
--==============================================================
create table aweb_user_role (
   USER_ID              VARCHAR(50)            not null,
   ROLE_ID              VARCHAR(50)            not null
);

INSERT INTO AWEB_USER_ROLE VALUES ('usr-admin', 'rol-QAv9DcTN');

--==============================================================
-- Table: "demo_excel"
--==============================================================
create table demo_excel (
   ID                   BIGINT                 not null
   generated  as identity,
   DATE                 DATE,
   NAME                 VARCHAR(255),
   NUMBER               INTEGER,
   CREATE_TIME          VARCHAR(255),
   CREATE_USER_ID       VARCHAR(255),
   CREATE_USER_NAME     VARCHAR(255),
   UPDATE_TIME          VARCHAR(255),
   UPDATE_USER_ID       VARCHAR(255),
   UPDATE_USER_NAME     VARCHAR(255),
   constraint PK_demo_excel primary key (ID)
);
