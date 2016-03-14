/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/11/17 10:04:37                            */
/*==============================================================*/

drop table if exists user_message;

/*==============================================================*/
/* Table: SMS_COUNT_RECORD                                           */
/*==============================================================*/
CREATE TABLE USER_MESSAGE
(
   ID               INT(20)     NOT NULL AUTO_INCREMENT,
   SENDERID         VARCHAR     NOT NULL      COMMENT '消息发送者',
   RECEIVERID       VARCHAR     NOT NULL      COMMENT '消息接收者',
   CONTENT      	TEXT                      COMMENT '消息内容',
   IS_READ          TINYINT(1)  DEFAULT 0     COMMENT '消息是否已读，0:未读，1：已读',
   SEND_TIME        DATETIME    NOT NULL DEFAULT NOW()      COMMENT '发送时间',
   RECEIVE_TIME     DATETIME    NOT NULL DEFAULT NOW()      COMMENT '接收时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB
DEFAULT CHARACTER SET = UTF8 COMMENT '私信消息列表';