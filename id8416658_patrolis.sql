-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 29, 2019 at 08:37 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id8416658_patrolis`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `ID_ACT` int(11) NOT NULL,
  `ID_PATROL` int(11) NOT NULL,
  `KETERANGAN` varchar(255) NOT NULL,
  `WAKTU` varchar(50) NOT NULL,
  `TANGGAL` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`ID_ACT`, `ID_PATROL`, `KETERANGAN`, `WAKTU`, `TANGGAL`) VALUES
(1, 4, 'COBA YA', '12:00', '10/01/2019'),
(2, 4, 'COBA 2', '13:00', '10/01/2019'),
(3, 1, 'Memperbaiki duct', '12:00', '10/01/2019'),
(4, 4, 'Memperbaiki Kabel 1', '10:34', '18/01/2019'),
(5, 4, 'Memperbaiki Kabel 2', '10:37', '18/01/2019'),
(17, 6, 'FIX 1', '09:42', '22/01/2019'),
(18, 6, 'FIX 2', '09:02', '23/01/2019'),
(19, 6, 'Memasukkan activity', '09:16', '28/01/2019'),
(20, 6, 'mengecek keadaan lingkungan', '09:32', '28/01/2019'),
(21, 7, 'Membersihkan ranting yang menghalangi jalan kabel', '14:28', '28/01/2019'),
(22, 6, 'memperbaiki slack', '14:35', '28/01/2019'),
(23, 6, 'merapikan kabel', '14:36', '28/01/2019'),
(24, 6, 'Mengecek tiang', '15:30', '28/01/2019');

-- --------------------------------------------------------

--
-- Table structure for table `patrol`
--

CREATE TABLE `patrol` (
  `ID_PATROL` int(11) NOT NULL,
  `ID_USER` int(6) NOT NULL,
  `TGL` varchar(50) NOT NULL,
  `WKT` varchar(50) NOT NULL,
  `RUAS` varchar(255) NOT NULL,
  `ACT` varchar(255) DEFAULT NULL,
  `OBJECT` varchar(255) DEFAULT NULL,
  `NODE_A` varchar(255) DEFAULT NULL,
  `NODE_B` varchar(255) DEFAULT NULL,
  `LINK` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `LATITUDE` varchar(255) DEFAULT NULL,
  `LONGITUDE` varchar(255) DEFAULT NULL,
  `PICT` varchar(255) DEFAULT NULL,
  `STATE` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patrol`
--

INSERT INTO `patrol` (`ID_PATROL`, `ID_USER`, `TGL`, `WKT`, `RUAS`, `ACT`, `OBJECT`, `NODE_A`, `NODE_B`, `LINK`, `STATUS`, `LATITUDE`, `LONGITUDE`, `PICT`, `STATE`) VALUES
(1, 1, '09/01/2019', '12:00:00', 'SEMARANG - SOLO', 'Memperbaiki duct', 'Duct', 'Node C', 'Node F', 'Node C - Node F', 'Baik', '-6.9825925', '110.4012137', 'asda.jpg', 'CLOSE'),
(4, 1, '10/01/2019', '10:10:05', 'SEMARANG - AMBARAWA', 'Memperbaiki Kabel 2', 'Kabel', 'Node B', 'Node E', 'Node B - Node E', 'setengah jalan', '-6.9945835', '110.4154831', 'uu.jpg', 'CLOSE'),
(5, 1, '11/01/2019', '11:20:05', 'TEMBALANG - PEDURUNGAN', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'EXPIRED'),
(6, 1, '30/01/2019', '15:30:10', 'SOLO - YOGYA', 'Mengecek tiang', 'Pole', 'Node D', 'Node F', 'Node D - Node F', 'Selesai', '-6.9939603', '110.4217781', 'b3745196-0fe9-4a67-9ec5-8f77a6e2bc7f.jpg', 'OPEN'),
(7, 3, '11/01/2019', '15:30:10', 'SOLO - SEMARANG', 'Membersihkan ranting yang menghalangi jalan kabel', 'Lain-Lain', 'Node A', 'Node D', 'Node A - Node D', 'sangat kotor', '-6.9883857', '110.3931456', 'Screenshot_1.jpg', 'CLOSE'),
(8, 3, '25/1/2019', '12:00:20', 'SEMARANG - YOGYA', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'EXPIRED'),
(9, 3, '30/01/2019', '11:30:10', 'PATI - SEMARANG', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'OPEN');

-- --------------------------------------------------------

--
-- Table structure for table `presensi`
--

CREATE TABLE `presensi` (
  `ID_PRES` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `P_TGL` varchar(50) NOT NULL,
  `P_WKT` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `presensi`
--

INSERT INTO `presensi` (`ID_PRES`, `ID_USER`, `P_TGL`, `P_WKT`) VALUES
(1, 1, '14/01/2019', '14:00:44'),
(2, 4, '14/01/2019', '14:04:03'),
(9, 5, '14/01/2019', '21:02:52'),
(10, 5, '15/01/2019', '08:16:44'),
(11, 1, '15/01/2019', '12:59:03'),
(12, 1, '16/01/2019', '10:42:15'),
(13, 1, '17/01/2019', '11:51:36'),
(14, 1, '18/01/2019', '09:13:27'),
(15, 1, '21/01/2019', '13:26:05'),
(16, 1, '22/01/2019', '09:53:58'),
(17, 1, '23/01/2019', '09:02:39'),
(18, 1, '28/01/2019', '11:43:35'),
(19, 4, '28/01/2019', '11:47:36'),
(20, 3, '28/01/2019', '14:21:57'),
(21, 3, '29/01/2019', '10:13:38'),
(22, 1, '29/01/2019', '10:24:02');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID_USER` int(11) NOT NULL,
  `NAMA` varchar(50) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID_USER`, `NAMA`, `USERNAME`, `PASSWORD`) VALUES
(1, 'Favo Perdana H.S.', 'favophs', '$2y$10$VU2MGHQ/srUniXWMIBFN0uhfX1s05Q8ymLLUihniMjfsn3G7kf1CK'),
(2, 'Rizki', 'rizki0502', '$2y$10$EUgXNZp4hpxXEyYnXzlw3eM60OBPAVl38CBzXHVKE0pYqgUP.REu2'),
(3, 'Alfian A F', 'alfianaf', '$2y$10$rIN3fkrvmemhJQh2ccKUiubo.E6G4WMCxjE5N4AW12tWfubm9uQo2'),
(4, 'M Ikhsan', 'ikhsan', '$2y$10$XpGqudCkgBaEqLw06SV8G.DdhS.RGvRPcj0Zig2UNJ/1WVlaygs8G'),
(5, 'Rio Kisna', 'riokisna', '$2y$10$i4vhlOkKowmyW99.eg1KgeRbuSJepCCotScQE7/F7TtH55F01Pcz2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`ID_ACT`),
  ADD KEY `activity_fk` (`ID_PATROL`);

--
-- Indexes for table `patrol`
--
ALTER TABLE `patrol`
  ADD PRIMARY KEY (`ID_PATROL`),
  ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `presensi`
--
ALTER TABLE `presensi`
  ADD PRIMARY KEY (`ID_PRES`),
  ADD KEY `presensi_fk` (`ID_USER`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID_USER`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `ID_ACT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `patrol`
--
ALTER TABLE `patrol`
  MODIFY `ID_PATROL` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `presensi`
--
ALTER TABLE `presensi`
  MODIFY `ID_PRES` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID_USER` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `activity_fk` FOREIGN KEY (`ID_PATROL`) REFERENCES `patrol` (`ID_PATROL`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `patrol`
--
ALTER TABLE `patrol`
  ADD CONSTRAINT `patrol_fk` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID_USER`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `presensi`
--
ALTER TABLE `presensi`
  ADD CONSTRAINT `presensi_fk` FOREIGN KEY (`ID_USER`) REFERENCES `user` (`ID_USER`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
