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
