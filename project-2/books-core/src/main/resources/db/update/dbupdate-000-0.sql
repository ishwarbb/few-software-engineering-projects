SET IGNORECASE TRUE;
create memory table T_AUTHENTICATION_TOKEN ( AUT_ID_C varchar(36) not null, AUT_IDUSER_C varchar(36) not null, AUT_LONGLASTED_B bit not null, AUT_CREATIONDATE_D datetime not null, AUT_LASTCONNECTIONDATE_D datetime, primary key (AUT_ID_C) );
create memory table T_BASE_FUNCTION ( BAF_ID_C varchar(20) not null, primary key (BAF_ID_C) );
create memory table T_CONFIG ( CFG_ID_C varchar(50) not null, CFG_VALUE_C varchar(250) not null, primary key (CFG_ID_C) );
create memory table T_LOCALE ( LOC_ID_C varchar(10) not null, primary key (LOC_ID_C) );
create memory table T_USER ( USE_ID_C varchar(36) not null, USE_IDLOCALE_C varchar(10) not null, USE_IDROLE_C varchar(36) not null, USE_USERNAME_C varchar(50) not null, USE_PASSWORD_C varchar(60) not null, USE_EMAIL_C varchar(100) not null, USE_THEME_C varchar(100) not null, USE_FIRSTCONNECTION_B bit not null, USE_CREATEDATE_D datetime not null, USE_DELETEDATE_D datetime, primary key (USE_ID_C) );
create memory table T_ROLE ( ROL_ID_C varchar(36) not null, ROL_NAME_C varchar(36) not null, ROL_CREATEDATE_D datetime not null, ROL_DELETEDATE_D datetime, primary key (ROL_ID_C) );
create memory table T_ROLE_BASE_FUNCTION ( RBF_ID_C varchar(36) not null, RBF_IDROLE_C varchar(36) not null, RBF_IDBASEFUNCTION_C varchar(20) not null, RBF_CREATEDATE_D datetime not null, RBF_DELETEDATE_D datetime, primary key (RBF_ID_C) );
create cached table T_BOOK ( BOK_ID_C varchar(36) not null, BOK_TITLE_C varchar(255) not null, BOK_SUBTITLE_C varchar(255), BOK_AUTHOR_C varchar(255) not null, BOK_DESCRIPTION_C varchar(4000), BOK_ISBN10_C varchar(10), BOK_ISBN13_C varchar(13), BOK_PAGECOUNT_N integer, BOK_LANGUAGE_C varchar(2), BOK_PUBLISHDATE_D datetime, primary key (BOK_ID_C) );
create cached table T_USER_BOOK ( UBK_ID_C varchar(36) not null, UBK_IDBOOK_C varchar(36) not null, UBK_IDUSER_C varchar(36) not null, UBK_CREATEDATE_D datetime not null, UBK_DELETEDATE_D datetime, UBK_READDATE_D datetime, primary key (UBK_ID_C) );
create cached table T_TAG ( TAG_ID_C varchar(36) not null, TAG_IDUSER_C varchar(36) not null, TAG_NAME_C varchar(36) not null, TAG_COLOR_C varchar(7) default '#3a87ad' not null, TAG_CREATEDATE_D datetime, TAG_DELETEDATE_D datetime, primary key (TAG_ID_C) );
create cached table T_USER_BOOK_TAG ( BOT_ID_C varchar(36) not null, BOT_IDUSERBOOK_C varchar(36) not null, BOT_IDTAG_C varchar(36) not null, primary key (BOT_ID_C) );
create cached table T_AUDIOBOOK ( AUB_ID_C varchar(36) not null, AUB_TITLE_C varchar(255) not null, AUB_AUTHOR_C varchar(36) not null, AUB_DESCRIPTION_C varchar(4000) not null, AUB_LANGUAGE_C varchar(40) , AUB_PUBLISHDATE_D datetime , AUB_URL varchar(500) , primary key (AUB_ID_C) );
create cached table T_USER_AUDIOBOOK ( UAB_ID_C varchar(36) not null, UAB_IDAUB_C varchar(36) not null, UAB_IDUSER_C varchar(36) not null, UAB_CREATEDATE_D datetime not null, UAB_DELETEDATE_D datetime, UAB_LISTENEDDATE_D datetime, primary key (UAB_ID_C) );
create cached table T_PODCAST ( POD_ID_C varchar(36) not null, POD_TITLE_C varchar(255) not null, POD_ARTIST_C varchar(255) , POD_RELEASEDATE_D datetime , POD_URL varchar(500) , primary key (POD_ID_C) );
create cached table T_USER_PODCAST ( UPD_ID_C varchar(36) not null, UPD_IDPOD_C varchar(36) not null, UPD_IDUSER_C varchar(36) not null, UPD_CREATEDATE_D datetime not null, UPD_DELETEDATE_D datetime, UPD_LISTENEDDATE_D datetime, primary key (UPD_ID_C) );

create cached table T_LIB_BOOK ( LBK_ID_C varchar(36) not null, LBK_IDBOOK_C varchar(36) not null, LBK_DELETEDATE_D datetime, primary key (LBK_ID_C) );
create cached table T_LIB_BOOK_GENRE ( GEN_ID_C varchar(36) not null, GEN_IDLIBBOOK_C varchar(36) not null, GEN_NAME_C varchar(36) not null, primary key (GEN_ID_C) );
create cached table T_LIB_BOOK_RATING ( RAT_ID_C varchar(36) not null, RAT_IDUSER_C varchar(36) not null, RAT_IDLIBBOOK_C varchar(36) not null, RAT_VALUE_C float not null, primary key (RAT_ID_C) );

alter table T_AUTHENTICATION_TOKEN add constraint FK_AUT_IDUSER_C foreign key (AUT_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
alter table T_USER add constraint FK_USE_IDLOCALE_C foreign key (USE_IDLOCALE_C) references T_LOCALE (LOC_ID_C) on delete restrict on update restrict;
alter table T_USER add constraint FK_USE_IDROLE_C foreign key (USE_IDROLE_C) references T_ROLE (ROL_ID_C) on delete restrict on update restrict;
alter table T_ROLE_BASE_FUNCTION add constraint FK_RBF_IDROLE_C foreign key (RBF_IDROLE_C) references T_ROLE (ROL_ID_C) on delete restrict on update restrict;
alter table T_ROLE_BASE_FUNCTION add constraint FK_RBF_IDBASEFUNCTION_C foreign key (RBF_IDBASEFUNCTION_C) references T_BASE_FUNCTION (BAF_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK add constraint FK_UBK_IDBOOK_C foreign key (UBK_IDBOOK_C) references T_BOOK (BOK_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK add constraint FK_UBK_IDUSER_C foreign key (UBK_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
alter table T_TAG add constraint FK_TAG_IDUSER_C foreign key (TAG_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK_TAG add constraint FK_BOT_IDUSERBOOK_C foreign key (BOT_IDUSERBOOK_C) references T_USER_BOOK (UBK_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK_TAG add constraint FK_BOT_IDTAG_C foreign key (BOT_IDTAG_C) references T_TAG (TAG_ID_C) on delete restrict on update restrict;
insert into T_CONFIG(CFG_ID_C, CFG_VALUE_C) values('DB_VERSION', '0');
insert into T_CONFIG(CFG_ID_C, CFG_VALUE_C) values('API_KEY_GOOGLE', '');
insert into T_BASE_FUNCTION(BAF_ID_C) values('ADMIN');
insert into T_LOCALE(LOC_ID_C) values('en');
insert into T_LOCALE(LOC_ID_C) values('fr');
insert into T_ROLE(ROL_ID_C, ROL_NAME_C, ROL_CREATEDATE_D) values('admin', 'Admin', NOW());
insert into T_ROLE(ROL_ID_C, ROL_NAME_C, ROL_CREATEDATE_D) values('user', 'User', NOW());
insert into T_ROLE_BASE_FUNCTION(RBF_ID_C, RBF_IDROLE_C, RBF_IDBASEFUNCTION_C, RBF_CREATEDATE_D) values('admin_ADMIN', 'admin', 'ADMIN', NOW());
insert into T_USER(USE_ID_C, USE_IDLOCALE_C, USE_IDROLE_C, USE_USERNAME_C, USE_PASSWORD_C, USE_EMAIL_C, USE_THEME_C, USE_FIRSTCONNECTION_B, USE_CREATEDATE_D) values('admin', 'en', 'admin', 'admin', '$2a$05$6Ny3TjrW3aVAL1or2SlcR.fhuDgPKp5jp.P9fBXwVNePgeLqb4i3C', 'admin@localhost', 'default.less', true, NOW());

alter table T_LIB_BOOK add constraint FK_LBK_IDBOOK_C foreign key (LBK_IDBOOK_C) references T_BOOK (BOK_ID_C) on delete restrict on update restrict;
alter table T_LIB_BOOK_GENRE add constraint FK_GEN_IDLIBBOOK_C foreign key (GEN_IDLIBBOOK_C) references T_LIB_BOOK (LBK_ID_C) on delete restrict on update restrict;
alter table T_LIB_BOOK_RATING add constraint FK_RAT_IDLIBBOOK_C foreign key (RAT_IDLIBBOOK_C) references T_LIB_BOOK (LBK_ID_C) on delete restrict on update restrict;
-- alter table T_LIB_BOOK_RATING add constraint FK_RAT_IDUSER_C foreign key (RAT_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
-- IDUSER is understood as username for now