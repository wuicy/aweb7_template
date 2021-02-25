-- ----------------------------
-- Table structure for AWEB_AUTHORITY
-- ----------------------------
DROP TABLE "AWEB_AUTHORITY";
CREATE TABLE "AWEB_AUTHORITY" (
  "ID" NVARCHAR2(50) NOT NULL ,
  "CREATE_TIME" NVARCHAR2(255) ,
  "CREATE_USER_ID" NVARCHAR2(255) ,
  "CREATE_USER_NAME" NVARCHAR2(255) ,
  "UPDATE_TIME" NVARCHAR2(255) ,
  "UPDATE_USER_ID" NVARCHAR2(255) ,
  "UPDATE_USER_NAME" NVARCHAR2(255) ,
  "DESC" NVARCHAR2(255) ,
  "NAME" NVARCHAR2(255) NOT NULL ,
  "PARENT_ID" NVARCHAR2(50) 
)
;

-- ----------------------------
-- Records of "AWEB_AUTHORITY"
-- ----------------------------
INSERT INTO "AWEB_AUTHORITY" VALUES ('auth-d2CHlFWd', '1577254488052', 'usr-admin', 'admin', '1577254488052', 'usr-admin', 'admin', '管理权限的权限', 'authorityManagement', NULL);
INSERT INTO "AWEB_AUTHORITY" VALUES ('auth-hg1Iu2lG', '1577254471036', 'usr-admin', 'admin', '1577254471036', 'usr-admin', 'admin', '管理角色的权限', 'roleManagement', NULL);
INSERT INTO "AWEB_AUTHORITY" VALUES ('auth-l4ZqCT4J', '1578452859460', 'usr-admin', 'admin', '1578452859460', 'usr-admin', 'admin', '管理菜单的权限', 'menuManagement', NULL);
INSERT INTO "AWEB_AUTHORITY" VALUES ('auth-OLVmaPOj', '1577254457368', 'usr-admin', 'admin', '1577254457368', 'usr-admin', 'admin', '管理用户的权限', 'userManagement', NULL);
COMMIT;

-- ----------------------------
-- Table structure for AWEB_FILE
-- ----------------------------
DROP TABLE "AWEB_FILE";
CREATE TABLE "AWEB_FILE" (
  "ID" NVARCHAR2(50) NOT NULL ,
  "GROUP_PATH" NVARCHAR2(255) ,
  "NAME" NVARCHAR2(255) ,
  "NODE_PATH" NVARCHAR2(255) ,
  "SIZE" NUMBER(20) ,
  "SUFFIX_NAME" NVARCHAR2(255) 
)
;

-- ----------------------------
-- Table structure for AWEB_MENU
-- ----------------------------
DROP TABLE "AWEB_MENU";
CREATE TABLE "AWEB_MENU" (
  "ID" NVARCHAR2(50) NOT NULL ,
  "CREATE_TIME" NVARCHAR2(255) ,
  "CREATE_USER_ID" NVARCHAR2(255) ,
  "CREATE_USER_NAME" NVARCHAR2(255) ,
  "UPDATE_TIME" NVARCHAR2(255) ,
  "UPDATE_USER_ID" NVARCHAR2(255) ,
  "UPDATE_USER_NAME" NVARCHAR2(255) ,
  "NAME" NVARCHAR2(255) NOT NULL ,
  "ORDER" NUMBER(11) ,
  "PARENT_ID" NVARCHAR2(50) ,
  "TITLE" NVARCHAR2(50) NOT NULL ,
  "DESC" NVARCHAR2(255) ,
  "ICON" NVARCHAR2(50) ,
  "PATH" NVARCHAR2(400) ,
  "DEPLOY" NUMBER(1)
)
;

-- ----------------------------
-- Records of "AWEB_MENU"
-- ----------------------------
INSERT INTO "AWEB_MENU" VALUES ('file-362KK0Ra', '1578991960257', 'usr-admin', 'admin', '1581050117269', 'usr-admin', 'admin', 'userList', '2', 'file-RaEbwYZ3', '用户管理', '用户列表', 'el-icon-setting', 'user/userList', '0');
INSERT INTO "AWEB_MENU" VALUES ('file-4DQOBJY9', '1580805691150', 'usr-admin', 'admin', '1581050111314', 'usr-admin', 'admin', 'menuList', '5', 'file-RaEbwYZ3', '菜单管理', '', 'el-icon-setting', 'menu/menuList', '0');
INSERT INTO "AWEB_MENU" VALUES ('file-nuxpnaiU', '1580815008102', 'usr-admin', 'admin', '1581050080711', 'usr-admin', 'admin', 'authorityList', '4', 'file-RaEbwYZ3', '权限管理', '', 'el-icon-setting', 'authority/authorityList', '0');
INSERT INTO "AWEB_MENU" VALUES ('file-PNmjz9PU', '1578991896698', 'usr-admin', 'admin', '1581050122226', 'usr-admin', 'admin', 'roleList', '3', 'file-RaEbwYZ3', '角色管理', '角色列表', 'el-icon-setting', 'role/roleList', '0');
INSERT INTO "AWEB_MENU" VALUES ('file-RaEbwYZ3', '1581049773800', 'usr-admin', 'admin', '1581999128764', 'usr-admin', 'admin', 'userAuthorityManagement', '1', '', '用户权限管理', '', 'el-icon-user-solid', '', '0');
COMMIT;

-- ----------------------------
-- Table structure for AWEB_ROLE
-- ----------------------------
DROP TABLE "AWEB_ROLE";
CREATE TABLE "AWEB_ROLE" (
  "ID" NVARCHAR2(50) NOT NULL ,
  "CREATE_TIME" NVARCHAR2(255) ,
  "CREATE_USER_ID" NVARCHAR2(255) ,
  "CREATE_USER_NAME" NVARCHAR2(255) ,
  "UPDATE_TIME" NVARCHAR2(255) ,
  "UPDATE_USER_ID" NVARCHAR2(255) ,
  "UPDATE_USER_NAME" NVARCHAR2(255) ,
  "DESC" NVARCHAR2(255) ,
  "NAME" NVARCHAR2(255) NOT NULL ,
  "PERMISSIONS" NVARCHAR2(1024)
)
;

-- ----------------------------
-- Records of "AWEB_ROLE"
-- ----------------------------
INSERT INTO "AWEB_ROLE" VALUES ('rol-QAv9DcTN', '1577254344223', 'usr-admin', 'admin', '1583227806273', 'usr-admin', 'admin', '管理员，拥有管理用户、角色、权限、菜单这四者的权限', 'ROLE_ADMIN' , '');
COMMIT;

-- ----------------------------
-- Table structure for AWEB_ROLE_AUTHORITY
-- ----------------------------
DROP TABLE "AWEB_ROLE_AUTHORITY";
CREATE TABLE "AWEB_ROLE_AUTHORITY" (
  "ROLE_ID" NVARCHAR2(50) NOT NULL ,
  "AUTHORITY_ID" NVARCHAR2(50) NOT NULL 
)
;

-- ----------------------------
-- Records of "AWEB_ROLE_AUTHORITY"
-- ----------------------------
INSERT INTO "AWEB_ROLE_AUTHORITY" VALUES ('rol-QAv9DcTN', 'auth-d2CHlFWd');
INSERT INTO "AWEB_ROLE_AUTHORITY" VALUES ('rol-QAv9DcTN', 'auth-hg1Iu2lG');
INSERT INTO "AWEB_ROLE_AUTHORITY" VALUES ('rol-QAv9DcTN', 'auth-l4ZqCT4J');
INSERT INTO "AWEB_ROLE_AUTHORITY" VALUES ('rol-QAv9DcTN', 'auth-OLVmaPOj');
COMMIT;

-- ----------------------------
-- Table structure for AWEB_ROLE_MENU
-- ----------------------------
DROP TABLE "AWEB_ROLE_MENU";
CREATE TABLE "AWEB_ROLE_MENU" (
  "ROLE_ID" NVARCHAR2(50) NOT NULL ,
  "MENU_ID" NVARCHAR2(50) NOT NULL 
)
;

-- ----------------------------
-- Records of "AWEB_ROLE_MENU"
-- ----------------------------
INSERT INTO "AWEB_ROLE_MENU" VALUES ('rol-QAv9DcTN', 'file-362KK0Ra');
INSERT INTO "AWEB_ROLE_MENU" VALUES ('rol-QAv9DcTN', 'file-PNmjz9PU');
INSERT INTO "AWEB_ROLE_MENU" VALUES ('rol-QAv9DcTN', 'file-nuxpnaiU');
INSERT INTO "AWEB_ROLE_MENU" VALUES ('rol-QAv9DcTN', 'file-4DQOBJY9');
INSERT INTO "AWEB_ROLE_MENU" VALUES ('rol-QAv9DcTN', 'file-RaEbwYZ3');
COMMIT;

-- ----------------------------
-- Table structure for AWEB_USER
-- ----------------------------
DROP TABLE "AWEB_USER";
CREATE TABLE "AWEB_USER" (
  "ID" NVARCHAR2(50) NOT NULL ,
  "CREATE_TIME" NVARCHAR2(255) ,
  "CREATE_USER_ID" NVARCHAR2(255) ,
  "CREATE_USER_NAME" NVARCHAR2(255) ,
  "UPDATE_TIME" NVARCHAR2(255) ,
  "UPDATE_USER_ID" NVARCHAR2(255) ,
  "UPDATE_USER_NAME" NVARCHAR2(255) ,
  "DESC" NVARCHAR2(255) ,
  "EMAIL" NVARCHAR2(100) ,
  "NAME" NVARCHAR2(50) NOT NULL ,
  "NICKNAME" NVARCHAR2(200) ,
  "PASSWORD" NVARCHAR2(128) NOT NULL ,
  "PHONE" NVARCHAR2(18) ,
  "STATUS" NVARCHAR2(2) 
)
;

-- ----------------------------
-- Records of "AWEB_USER"
-- ----------------------------
INSERT INTO "AWEB_USER" VALUES ('usr-admin', '', '', '', '1581050800138', 'usr-admin', 'admin', '', '', 'admin', '管理员', '$2a$10$852p2TT7sJjZEHE64liF.ObcvwIYTfJ4FYE4s1c4Z/.IuwWD89QDa', '', '1');
COMMIT;

-- ----------------------------
-- Table structure for AWEB_USER_ROLE
-- ----------------------------
DROP TABLE "AWEB_USER_ROLE";
CREATE TABLE "AWEB_USER_ROLE" (
  "USER_ID" NVARCHAR2(50) NOT NULL ,
  "ROLE_ID" NVARCHAR2(50) NOT NULL 
)
;

-- ----------------------------
-- Records of "AWEB_USER_ROLE"
-- ----------------------------
INSERT INTO "AWEB_USER_ROLE" VALUES ('usr-admin', 'rol-QAv9DcTN');
COMMIT;

-- ----------------------------
-- Table structure for DEMO_EXCEL
-- ----------------------------
DROP TABLE "DEMO_EXCEL";
CREATE TABLE "DEMO_EXCEL" (
  "ID" NUMBER(20) NOT NULL ,
  "DATE" DATE ,
  "NAME" NVARCHAR2(255) ,
  "NUMBER" NUMBER(11) 
)
;

-- ----------------------------
-- Primary Key structure for table AWEB_AUTHORITY
-- ----------------------------
ALTER TABLE "AWEB_AUTHORITY" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table AWEB_AUTHORITY
-- ----------------------------
CREATE UNIQUE INDEX "UK_f0cmfe8wqlt27hx76n0h9o2yf"
  ON "AWEB_AUTHORITY" ("NAME");

-- ----------------------------
-- Primary Key structure for table AWEB_FILE
-- ----------------------------
ALTER TABLE "AWEB_FILE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Primary Key structure for table AWEB_MENU
-- ----------------------------
ALTER TABLE "AWEB_MENU" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table AWEB_MENU
-- ----------------------------
CREATE UNIQUE INDEX "UK_oyl0vtq40kyv8fdfjuade2yqb"
  ON "AWEB_MENU" ("NAME");

-- ----------------------------
-- Primary Key structure for table AWEB_ROLE
-- ----------------------------
ALTER TABLE "AWEB_ROLE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table AWEB_ROLE
-- ----------------------------
CREATE UNIQUE INDEX "UK_1jgik2hgsmfm7g4ee2nf5m6h3"
  ON "AWEB_ROLE" ("NAME");

-- ----------------------------
-- Primary Key structure for table AWEB_USER
-- ----------------------------
ALTER TABLE "AWEB_USER" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table AWEB_USER
-- ----------------------------
CREATE UNIQUE INDEX "UK_kpjtmcyew2jx0jn8ouw1jmw2n"
  ON "AWEB_USER" ("NAME");

-- ----------------------------
-- Primary Key structure for table DEMO_EXCEL
-- ----------------------------
ALTER TABLE "DEMO_EXCEL" ADD PRIMARY KEY ("ID");
