# 用户表
CREATE TABLE `visitors` (
  `uid` varchar(16) NOT NULL,
  `upassword` varchar(16) NOT NULL,
  `utel` varchar(16) NOT NULL,
  `uemail` varchar(32) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `visitors_uemail_uindex` (`uemail`),
  UNIQUE KEY `visitors_utel_uindex` (`utel`),
  UNIQUE KEY `visitors_uid_uindex` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 导生表
CREATE TABLE `guiders` (
  `guidername` varchar(255) NOT NULL,
  `guidermajor` varchar(255) NOT NULL,
  `state` tinyint(1) NOT NULL DEFAULT '0',
   PRIMARY KEY (`guidername`),
   CONSTRAINT `guiders_ibfk_1` FOREIGN KEY (`guidername`) REFERENCES `visitors` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
#历史记录表
CREATE TABLE `messagerecords` (
    `sender` varchar(255) NOT NULL,
    `recevier` varchar(255) NOT NULL,
     `message` varchar(255) NOT NULL,
    `time` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;