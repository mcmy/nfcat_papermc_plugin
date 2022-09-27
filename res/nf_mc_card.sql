-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2022-09-27 16:15:51
-- 服务器版本： 8.0.12
-- PHP 版本： 5.6.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `cloud`
--

-- --------------------------------------------------------

--
-- 表的结构 `nf_mc_card`
--

CREATE TABLE `nf_mc_card` (
  `card` varchar(32) NOT NULL COMMENT '卡密',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '1->金币;2->水晶;3->VIP;4->SVIP',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `use_mc_name` varchar(20) DEFAULT NULL COMMENT '使用者名称',
  `info` text COMMENT '其他信息'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转储表的索引
--

--
-- 表的索引 `nf_mc_card`
--
ALTER TABLE `nf_mc_card`
  ADD PRIMARY KEY (`card`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
