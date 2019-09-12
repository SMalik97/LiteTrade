-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 22, 2019 at 01:54 AM
-- Server version: 10.2.26-MariaDB-cll-lve
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hsgawb_android`
--

-- --------------------------------------------------------

--
-- Table structure for table `litetrade_reg`
--

CREATE TABLE `litetrade_reg` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `email` text NOT NULL,
  `phone` text NOT NULL,
  `dob` text NOT NULL,
  `gender` text NOT NULL,
  `password` text NOT NULL,
  `profile_pic` text DEFAULT NULL,
  `red_challenge` double NOT NULL DEFAULT 0,
  `green_challenge` double NOT NULL DEFAULT 0,
  `total_balance` double NOT NULL DEFAULT 0,
  `wining_blance` double NOT NULL DEFAULT 0,
  `id_name` varchar(255) DEFAULT NULL,
  `id_number` varchar(255) DEFAULT NULL,
  `ifsc` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `account_no` varchar(255) DEFAULT NULL,
  `acc_hld_name` varchar(255) DEFAULT NULL,
  `acc_ph_no` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `litetrade_reg`
--

INSERT INTO `litetrade_reg` (`id`, `name`, `email`, `phone`, `dob`, `gender`, `password`, `profile_pic`, `red_challenge`, `green_challenge`, `total_balance`, `wining_blance`, `id_name`, `id_number`, `ifsc`, `bank_name`, `account_no`, `acc_hld_name`, `acc_ph_no`) VALUES
(2, 'Subrata', 'subratamalik1997@gmail.com', '8', '21/8/2019', 'Male', '0cc175b9c0f1b6a831c399e269772661', 'https://hsgawb.com/LiteTrade/uploads/subratamalik1997@gmail.com.png', 58, 14.52, 0, 0, 'Voter Id', '122', 'Subrata', 'hj', 'gh', 'fg', 'asd');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `litetrade_reg`
--
ALTER TABLE `litetrade_reg`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `litetrade_reg`
--
ALTER TABLE `litetrade_reg`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
