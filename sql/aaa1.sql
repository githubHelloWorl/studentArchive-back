SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 用户表
drop table if exists user;
create table user
(
    id           bigint auto_increment primary key comment 'id',
    userAccount  varchar(256) not null comment '账号',
    userName     varchar(256) not null comment '用户姓名',
    userPassword varchar(512) not null comment '密码',
    cardId       varchar(256) not null comment '身份证号',
    userRole     varchar(256) not null comment '用户角色: student/teacher/admin/ban',
    phone        varchar(256) not null comment '手机号',
    department   varchar(256) not null comment '院系',
    classes      varchar(256) not null comment '班级',
    job          varchar(256) null comment '职务',
    duty         varchar(256) null comment '职称', -- 助教、讲师、副教授和教授
    unity        varchar(256) null comment '单位',
    updateTime   datetime     null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户' collate = utf8mb4_unicode_ci;

# insert into user(id,userAccount,userName,userPassword,userRole,department,class,phone,cardId)
# values(1,'12345678','admin','12345678','admin','department','class','phone','cardId');
# insert into user(id,userAccount,userName,userPassword,userRole,department,class,phone,cardId)
# values(2,'201996001','李胜利','12345678','teacher','department','class','15862987450','cardId');
# insert into user(id,userAccount,userName,userPassword,userRole,department,class,phone,cardId)
# values(3,'201996002','袁胜利','12345678','teacher','department','class','15169852236','cardId');
# insert into user(id,userAccount,userName,userPassword,userRole,department,class,phone,cardId)
# values(4,'202123001','袁胜利','12345678','student','department','class','15820026984','cardId');

insert into user(id, userAccount, userName, userPassword, cardId, userRole, phone, department, classes, job, duty, unity)
values (1, '12345678', 'admin', '12345678', '371432200305149874', 'admin', '15862987450', '', '','', '', '');
insert into user(id, userAccount, userName, userPassword, cardId, userRole, phone, department, classes, job, duty, unity)
values (2, '201996001', '李胜利', '12345678', '371432200305149204', 'teacher', '15169856325', '理学院', '数学','副教授', '辅导员', '教务处');
insert into user(id, userAccount, userName, userPassword, cardId, userRole, phone, department, classes, job, duty, unity)
values (3, '201996002', '李笑', '12345678', '371432200305144204', 'teacher', '15169816325', '商学院', '会计学','教授', '辅导员', '教务处');
insert into user(id, userAccount, userName, userPassword, cardId, userRole, phone, department, classes, job, duty, unity)
values (4, '201996003', '王龙', '12345678', '371432200305529804', 'teacher', '19365874580', '信息与工程学院', '教授', '教授', '讲师', '教务处');
-- insert into user(id, userAccount, userName, userPassword, cardId, userRole, phone, department, classes, job, duty, unity)
-- values (5, '202123001', '张三', '12345678', '371432225605149204', 'student', '15169856325', '理学院', '数学','', '', '');
-- insert into user(id, userAccount, userName, userPassword, cardId, userRole, phone, department, classes, job, duty, unity)
-- values (6, '202123002', '李四', '12345678', '371432200001144204', 'student', '15165420325', '商学院', '会计学','', '', '');
-- insert into user(id, userAccount, userName, userPassword, cardId, userRole, phone, department, classes, job, duty, unity)
-- values (7, '202123003', '王五', '12345678', '371432208895529804', 'student', '19858052369', '信息与工程学院', '通信工程', '', '讲师', '');


drop table if exists archive;
create table archive
(
    id         bigint auto_increment primary key comment 'id',
    sid        varchar(256) not null comment '学号',
    archiveId  varchar(256) null comment '档案编号',
    sex        varchar(256) null comment '性别',
    address    varchar(256) null comment '地址',
    health     varchar(256) null comment '健康',
    origin     varchar(256) null comment '生源地',
    nation     varchar(256) null comment '民族',
    createTime datetime     null comment '出生时间',
    updateTime datetime     null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '档案' collate = utf8mb4_unicode_ci;

drop table if exists rewardPunishInfo;
create table rewardPunishInfo
(
    id         bigint auto_increment primary key comment 'id',
    sid        varchar(256) not null comment '学号',
    tid        varchar(256) null comment '教师工号',
    fileId     varchar(256) not null comment '证件编号',
    fileName   varchar(256) not null comment '奖惩证书名称',
    state      varchar(256) not null comment '未审核-0/通过-1/不通过-2',
    fileInfo   varchar(256) not null comment '证书信息0-处分/1-证书',
    filePath   varchar(256) null comment '文件路径',
    fileUnit   varchar(256) null comment '颁发单位',
    tname      varchar(256) null comment '审核教师',
    fileTime   datetime     null default CURRENT_TIMESTAMP comment '颁发时间',
    submitTime datetime     null default CURRENT_TIMESTAMP comment '提交时间',
    checkTime  datetime     null comment '审核时间',
    stime      varchar(256) null comment '学年学期'
) comment '奖惩信息表' collate = utf8mb4_unicode_ci;

drop table if exists notice;
create table notice
(
    id            bigint auto_increment primary key comment 'id',
    noticeTitle   varchar(256)  not null comment '公告标题',
    noticeContent varchar(1024) null comment '公告内容',
    noticeTime    datetime      null default CURRENT_TIMESTAMP comment '发布时间',
    updateTime    datetime      null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '公告表' collate = utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;