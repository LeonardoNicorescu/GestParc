-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 03, 2024 alle 20:22
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestparc`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `devices`
--

CREATE TABLE `devices` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `serial_number` varchar(255) NOT NULL,
  `operating_system` varchar(255) NOT NULL,
  `model` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `devices`
--

INSERT INTO `devices` (`id`, `name`, `serial_number`, `operating_system`, `model`, `user_id`) VALUES
(1, 'Laptop', 'SN123456', 'Windows 10', 'Dell XPS 13', '1'),
(2, 'Desktop', 'SN789012', 'Windows 10', 'HP Pavilion', '2'),
(3, 'Tablet', 'SN345678', 'Android 11', 'Samsung Galaxy Tab', '3'),
(4, 'Smartphone', 'SN901234', 'iOS 14', 'iPhone 12', '4'),
(5, 'Server', 'SN567890', 'Ubuntu 20.04', 'Dell PowerEdge', '5'),
(6, 'Laptop', 'SN123457', 'Windows 10', 'MacBook Pro', '6'),
(7, 'Desktop', 'SN789013', 'Windows 10', 'Lenovo ThinkCentre', '7'),
(8, 'Tablet', 'SN345679', 'iOS 14', 'iPad Pro', '8'),
(9, 'Smartphone', 'SN901235', 'Android 11', 'Google Pixel 5', '9'),
(10, 'Server', 'SN567891', 'CentOS 8', 'HP ProLiant', '10'),
(11, 'Laptop', 'SN123458', 'Windows 10', 'Acer Aspire', '11'),
(12, 'Desktop', 'SN789014', 'Windows 10', 'ASUS VivoPC', '12'),
(13, 'Tablet', 'SN345680', 'Android 11', 'Huawei MediaPad', '13'),
(14, 'Smartphone', 'SN901236', 'iOS 14', 'iPhone SE', '14'),
(15, 'Server', 'SN567892', 'Debian 10', 'IBM System x', '15'),
(16, 'Laptop', 'SN123459', 'Windows 10', 'Microsoft Surface', '16'),
(17, 'Desktop', 'SN789015', 'Windows 10', 'Alienware Aurora', '17'),
(18, 'Tablet', 'SN345681', 'iOS 14', 'iPad Mini', '18'),
(19, 'Smartphone', 'SN901237', 'Android 11', 'OnePlus 8', '19'),
(20, 'Server', 'SN567893', 'Fedora 33', 'Supermicro SuperServer', '20');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `password`, `role`) VALUES
(0, 'Personne', 'Personne', 'Personne', '$2a$10$Ml.j1L.TV.RiANZqzC0p3O/B2X7/DmaudAyiGW.ALev7ic4xIPI3i', 1),
(1, 'admin', 'admin', 'admin', '$2a$10$Hpcw949fVSvZPzdVjOvI0eKFILqfpRzrtvWIcEyXXseFIa1a9Z5rS', 2),
(2, 'user', 'user', 'user', '$2a$10$XWd/Ts2wSZ4kf9hfJ2sn1uA.GqQ5BmM1mr9ILw6ePRe0Ge6Yq1z9i', 1),
(3, 'John', 'Doe', 'johndoe', '$2a$10$7sMl9tWyaIc7ZG.ePKJzluF8YB4.Z2TV3IMzz/uy68G0/IR4FhRS6', 1),
(4, 'Jane', 'Smith', 'janesmith', '$2a$10$TRNT/O3RP6yJ4x.vjfrJDe4P2LeT0qnrk90gXtXrQFRfuFexTAUgG', 2),
(5, 'Robert', 'Johnson', 'robertjohnson', '$2a$10$8bF.ZeGZvHPeDCkZ0BXOBug5tAULd72xOcD2LRIaBBfX8XVoK/yQK', 1),
(6, 'Michael', 'Williams', 'michaelwilliams', '$2a$10$sFVZ1Q2XJo.GwJGy7kC1keDPxIqCuzhv3ISIHv9BrQ9Fng8RxOltG', 2),
(7, 'Mary', 'Brown', 'marybrown', '$2a$10$D5nEdsnl.yYp9e2T/AlFlOpzGvqSyF2D0HugW7dx8HnLGGdQ7XpOa', 1),
(8, 'William', 'Jones', 'williamjones', '$2a$10$yBQ4Gy0xujLU2deU9ODfuOehDp0i5.PfH96cDAU4oMvgPKHD/4/2S', 1),
(9, 'Patricia', 'Garcia', 'patriciagarcia', '$2a$10$GdB.aSxPUbcl/z4XYAcGLOHHrrN4/fD9b9k5YcMJktphHbSc1o3E6', 2),
(10, 'Linda', 'Martinez', 'lindamartinez', '$2a$10$0xk/ztEt10g4aGOCxWxW7eTBuz1fgrINoZOECL.7/u5MhxFvEB/CO', 1),
(11, 'Thomas', 'Rodriguez', 'thomasrodriguez', '$2a$10$O8.nBvFuL6BflWQx0/9sSOjI7Saz0INp0m.UsQoCMfi3AjGocWfii', 1),
(12, 'Christopher', 'Hernandez', 'christopherhernandez', '$2a$10$ZDPZpDCcbl6WqrMhnSYfM.zQ7TR/N98Z7TrnUR4/ZZLkQZP8Q0qCG', 2),
(13, 'Barbara', 'Lopez', 'barbaralopez', '$2a$10$eUDv9p4tEu4K/yJ3k/Gy7uPdLcoKhGG3ITi.xgzmQCXoD1kTrUlwy', 1),
(14, 'Daniel', 'Gonzalez', 'danielgonzalez', '$2a$10$akGyjhpERDJNc3IN3cIE6O.T/EIt7u2ThtTZ0OTcwYO7ZP0.06hyG', 1),
(15, 'Susan', 'Wilson', 'susanwilson', '$2a$10$p1zG4G2hUOQ2qlMQ3xMaS.y2m.DPqBPArieBHT6o.T.1KQ0ZknuDC', 2),
(16, 'Paul', 'Anderson', 'paulanderson', '$2a$10$D8P/xpqu.TF/e9h0v89G1umU8DdIE1x4OFtG9.Q9ybLHvL1lkLjEq', 1),
(17, 'Karen', 'Thomas', 'karenthomas', '$2a$10$CxPfVqSzeQno4VLqvAX/.Oy/N4fXoOihGozm1E3TIYmfLeztGpYuW', 1),
(18, 'Steven', 'Taylor', 'steventaylor', '$2a$10$TQ7SGS7JNVEX7icENm0TTep8ge3V5yD3Hvn8xD4hOaJAY1KXcI08u', 2),
(19, 'Lisa', 'Moore', 'lisamoore', '$2a$10$ybwD5vPZZv3pCqf6O3.PyOWZy3kj.LK5Wy1v1ep5Rhd5.vZctOe7S', 1),
(20, 'James', 'Jackson', 'jamesjackson', '$2a$10$pJFCmLzZaXDA05EY3Aay2.qSxI.f3D1YOFVd5uJQ8lgH.yu8uIpCy', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `devices`
--
ALTER TABLE `devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
