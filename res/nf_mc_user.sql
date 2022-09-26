-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2022-09-26 10:25:49
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
-- 表的结构 `nf_mc_user`
--

CREATE TABLE `nf_mc_user` (
  `password` varchar(128) NOT NULL,
  `mc_name` varchar(20) NOT NULL,
  `gold` bigint(11) NOT NULL DEFAULT '10' COMMENT '金币',
  `cloud_nano_id` varchar(24) DEFAULT NULL COMMENT '对接云服务id'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转储表的索引
--

--
-- 表的索引 `nf_mc_user`
--
ALTER TABLE `nf_mc_user`
  ADD PRIMARY KEY (`mc_name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
