-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 27, 2019 at 04:43 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cuti`
--

-- --------------------------------------------------------

--
-- Table structure for table `cuti_karyawan`
--

CREATE TABLE `cuti_karyawan` (
  `id_cuti` int(11) NOT NULL,
  `nik_karyawan` int(11) NOT NULL,
  `tgl_mulai_cuti` date NOT NULL,
  `tgl_selesai_cuti` date NOT NULL,
  `jumlah_cuti` int(11) NOT NULL,
  `sisa_cuti` int(11) NOT NULL,
  `alasan_mengambil_cuti` varchar(50) NOT NULL,
  `hak_cuti` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cuti_karyawan`
--

INSERT INTO `cuti_karyawan` (`id_cuti`, `nik_karyawan`, `tgl_mulai_cuti`, `tgl_selesai_cuti`, `jumlah_cuti`, `sisa_cuti`, `alasan_mengambil_cuti`, `hak_cuti`) VALUES
(12001, 121213, '2019-06-16', '2019-06-17', 1, 3, 'Istirahat', 4),
(12002, 121212, '2019-06-16', '2019-06-19', 4, 0, 'Liburan', 4),
(12003, 121213, '2019-06-20', '2019-06-23', 3, 0, 'Liburan', 4),
(12004, 121214, '2019-06-18', '2019-06-20', 3, 1, 'Liburan', 4),
(12005, 121214, '2019-06-27', '2019-06-28', 2, 10, 'liburan', 12);

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `nik_karyawan` int(11) NOT NULL,
  `nama_karyawan` varchar(25) NOT NULL,
  `golongan` varchar(25) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `umur` int(11) NOT NULL,
  `jenis_kelamin` varchar(25) NOT NULL,
  `agama` varchar(25) NOT NULL,
  `kewarganegaraan` varchar(25) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `no_identitas` int(11) NOT NULL,
  `no_tlp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`nik_karyawan`, `nama_karyawan`, `golongan`, `tanggal_lahir`, `umur`, `jenis_kelamin`, `agama`, `kewarganegaraan`, `alamat`, `no_identitas`, `no_tlp`) VALUES
(121212, 'Karyo', 'Karyawan', '1993-04-16', 26, 'Laki-Laki', 'Kristen', 'Indonesia', 'Cisauk', 12345678, '089123412341'),
(121213, 'Udin', 'Karyawan', '1994-05-16', 25, 'Laki-Laki', 'Budha', 'Indonesia', 'Cisauk', 90909090, '089123456789'),
(121214, 'Faijo', 'Manager', '1972-06-18', 47, 'Laki-Laki', 'Islam', 'Indonesia', 'Serpong', 12389012, '089212341234');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `kd_user` int(11) NOT NULL,
  `nm_user` varchar(25) NOT NULL,
  `password` varchar(10) NOT NULL,
  `level` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`kd_user`, `nm_user`, `password`, `level`) VALUES
(121212, 'Ajeng', '123', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cuti_karyawan`
--
ALTER TABLE `cuti_karyawan`
  ADD PRIMARY KEY (`id_cuti`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`nik_karyawan`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
