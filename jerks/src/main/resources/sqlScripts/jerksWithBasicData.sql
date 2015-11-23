-- phpMyAdmin SQL Dump
-- version 4.0.10.10
-- http://www.phpmyadmin.net
--
-- Host: 127.7.32.130:3306
-- Generation Time: Nov 23, 2015 at 03:19 AM
-- Server version: 5.5.45
-- PHP Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `jerks`
--

-- --------------------------------------------------------

--
-- Table structure for table `ADDRESS`
--

CREATE TABLE IF NOT EXISTS `ADDRESS` (
  `ADDRESSID` int(11) NOT NULL AUTO_INCREMENT,
  `ADDRESSLINE1` varchar(255) DEFAULT NULL,
  `ADDRESSLINE2` varchar(255) DEFAULT NULL,
  `CITY` varchar(50) DEFAULT NULL,
  `COUNTRY` varchar(2) DEFAULT 'US',
  `LATITUDE` float DEFAULT '0',
  `LONGITUDE` float DEFAULT '0',
  `STATE` varchar(2) DEFAULT NULL,
  `ZIPCODE` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`ADDRESSID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `ADDRESS`
--

INSERT INTO `ADDRESS` (`ADDRESSID`, `ADDRESSLINE1`, `ADDRESSLINE2`, `CITY`, `COUNTRY`, `LATITUDE`, `LONGITUDE`, `STATE`, `ZIPCODE`) VALUES
(14, '360 Huntington', 'Apt 16', 'Boston', 'US', 42.337, -71.072, 'MA', '02115'),
(15, '200 Beacon St', 'Apt 1', 'Gotham', 'US', 42.337, -71.072, 'MA', '02115'),
(16, '400 Huntington St', 'Apt 1', 'Gotham', 'US', 42.337, -71.072, 'MA', '02115');

-- --------------------------------------------------------

--
-- Table structure for table `EVENT`
--

CREATE TABLE IF NOT EXISTS `EVENT` (
  `EVENTID` varchar(255) NOT NULL,
  `DATE` date NOT NULL,
  `DESCRIPTION` varchar(8000) DEFAULT NULL,
  `MINAGELIMIT` int(11) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `RATING` double DEFAULT NULL,
  `REMAININGTICKETS` int(11) DEFAULT NULL,
  `SOURCE` int(11) NOT NULL,
  `TICKETPRICE` double DEFAULT NULL,
  `ADDRESSID` int(11) NOT NULL,
  PRIMARY KEY (`EVENTID`),
  KEY `FK_EVENT_ADDRESSID` (`ADDRESSID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `EVENT`
--

INSERT INTO `EVENT` (`EVENTID`, `DATE`, `DESCRIPTION`, `MINAGELIMIT`, `NAME`, `RATING`, `REMAININGTICKETS`, `SOURCE`, `TICKETPRICE`, `ADDRESSID`) VALUES
('Sun Nov 22 22:12:59 EST 2015', '2015-11-22', 'This is THE event 1 !!', 21, 'event1', 5, 100, 2, 0, 16);

-- --------------------------------------------------------

--
-- Table structure for table `USER`
--

CREATE TABLE IF NOT EXISTS `USER` (
  `EMAIL` varchar(255) NOT NULL,
  `AREAOFINTEREST` varchar(1000) DEFAULT NULL,
  `DISLIKES` varchar(1000) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `FIRSTNAME` varchar(50) NOT NULL,
  `GENDER` varchar(1) DEFAULT NULL,
  `LASTNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(20) NOT NULL,
  `PHONENUMBER` varchar(10) DEFAULT NULL,
  `ADDRESSID` int(11) DEFAULT NULL,
  PRIMARY KEY (`EMAIL`),
  KEY `FK_USER_ADDRESSID` (`ADDRESSID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `USER`
--

INSERT INTO `USER` (`EMAIL`, `AREAOFINTEREST`, `DISLIKES`, `DOB`, `FIRSTNAME`, `GENDER`, `LASTNAME`, `PASSWORD`, `PHONENUMBER`, `ADDRESSID`) VALUES
('brucewayne@batman.movie', 'music|holiday', 'music|holiday', '1990-03-11', 'Bruce', 'M', 'Wayne', 'batmanPwd1234', '6179991111', 15);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `EVENT`
--
ALTER TABLE `EVENT`
  ADD CONSTRAINT `FK_EVENT_ADDRESSID` FOREIGN KEY (`ADDRESSID`) REFERENCES `ADDRESS` (`ADDRESSID`);

--
-- Constraints for table `USER`
--
ALTER TABLE `USER`
  ADD CONSTRAINT `FK_USER_ADDRESSID` FOREIGN KEY (`ADDRESSID`) REFERENCES `ADDRESS` (`ADDRESSID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
