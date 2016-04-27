-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 27-Abr-2016 às 21:18
-- Versão do servidor: 5.6.25
-- PHP Version: 5.6.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `javabank`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `conta`
--

CREATE TABLE IF NOT EXISTS `conta` (
  `Numero` bigint(20) unsigned NOT NULL,
  `Primeiro_Corr` varchar(12) NOT NULL,
  `Segundo_Corr` varchar(12) DEFAULT NULL,
  `Terceiro_Corr` varchar(12) DEFAULT NULL,
  `Saldo` double NOT NULL,
  `Limite` double NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `conta`
--

INSERT INTO `conta` (`Numero`, `Primeiro_Corr`, `Segundo_Corr`, `Terceiro_Corr`, `Saldo`, `Limite`) VALUES
(1, '45620178952', '12345678910', NULL, 25.5, 500),
(2, '45678912312', NULL, '11122233312', 305, 150);

-- --------------------------------------------------------

--
-- Estrutura da tabela `correntista`
--

CREATE TABLE IF NOT EXISTS `correntista` (
  `CPF` varchar(12) NOT NULL,
  `Nome` varchar(35) NOT NULL,
  `Senha` varchar(35) NOT NULL,
  `Endereco` varchar(45) NOT NULL,
  `Email` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `correntista`
--

INSERT INTO `correntista` (`CPF`, `Nome`, `Senha`, `Endereco`, `Email`) VALUES
('11122233312', 'Carlos', '123456', 'Rua um', 'carlos@carlos.com'),
('12345678910', 'Mario', '123456', 'Endereço', 'mario@luigi.com'),
('45620178952', 'Carlos José', '123456', 'Rua do Carlos', 'carlos@ct.com'),
('45678912312', 'Joao', '123456', 'Rua do Joao', 'joao@joao.com');

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario`
--

CREATE TABLE IF NOT EXISTS `funcionario` (
  `Codigo` bigint(20) unsigned NOT NULL,
  `Nome` varchar(35) NOT NULL,
  `Senha` varchar(35) NOT NULL,
  `Email` varchar(25) NOT NULL,
  `Funcao` enum('gerente','caixa','atendente') NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `funcionario`
--

INSERT INTO `funcionario` (`Codigo`, `Nome`, `Senha`, `Email`, `Funcao`) VALUES
(1, 'Jorge David', '123456', 'jorgedjr21@gmail.com', 'gerente'),
(5, 'Antonio', '123456', 'antonio@an.ic', 'caixa'),
(6, 'Joao', '123456', 'jorgedjr21@gmail.com', 'atendente');

-- --------------------------------------------------------

--
-- Estrutura da tabela `transacao`
--

CREATE TABLE IF NOT EXISTS `transacao` (
  `Codigo` bigint(20) unsigned NOT NULL,
  `Tipo` enum('deposito','saque','pagto','transf') NOT NULL,
  `Nro_Conta` bigint(20) unsigned NOT NULL,
  `Nro_Conta_Transf` bigint(20) unsigned DEFAULT NULL,
  `Valor` double NOT NULL,
  `data` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `transacao`
--

INSERT INTO `transacao` (`Codigo`, `Tipo`, `Nro_Conta`, `Nro_Conta_Transf`, `Valor`, `data`) VALUES
(1, 'saque', 1, NULL, 23.5, '2016-04-12 13:03:36'),
(2, 'saque', 1, NULL, 25, '2016-04-12 13:15:23'),
(3, 'deposito', 1, NULL, 100, '2016-04-12 13:26:39'),
(4, 'deposito', 1, NULL, 150, '2016-04-12 13:29:30'),
(5, 'saque', 1, NULL, 25, '2016-04-12 13:31:19'),
(6, 'deposito', 1, NULL, 50, '2016-04-13 12:51:07'),
(7, 'saque', 1, NULL, 95, '2016-04-13 12:51:26'),
(8, 'pagto', 1, NULL, 50, '2016-04-13 13:17:27'),
(9, 'transf', 1, 2, 150, '2016-04-20 13:18:52'),
(10, 'transf', 1, 2, 150, '2016-04-27 14:21:12'),
(11, 'transf', 1, 2, 5, '2016-04-27 14:33:52'),
(12, 'deposito', 1, NULL, 125.5, '2016-04-27 14:34:42');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `conta`
--
ALTER TABLE `conta`
  ADD PRIMARY KEY (`Numero`),
  ADD KEY `Primeiro_Corr` (`Primeiro_Corr`),
  ADD KEY `Segundo_Corr` (`Segundo_Corr`),
  ADD KEY `Terceiro_Corr` (`Terceiro_Corr`);

--
-- Indexes for table `correntista`
--
ALTER TABLE `correntista`
  ADD PRIMARY KEY (`CPF`);

--
-- Indexes for table `funcionario`
--
ALTER TABLE `funcionario`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indexes for table `transacao`
--
ALTER TABLE `transacao`
  ADD PRIMARY KEY (`Codigo`),
  ADD KEY `Nro_Conta` (`Nro_Conta`),
  ADD KEY `Nro_Conta_Transf` (`Nro_Conta_Transf`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `conta`
--
ALTER TABLE `conta`
  MODIFY `Numero` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `funcionario`
--
ALTER TABLE `funcionario`
  MODIFY `Codigo` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `transacao`
--
ALTER TABLE `transacao`
  MODIFY `Codigo` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `conta`
--
ALTER TABLE `conta`
  ADD CONSTRAINT `CONTA_ibfk_1` FOREIGN KEY (`Primeiro_Corr`) REFERENCES `correntista` (`CPF`) ON UPDATE CASCADE,
  ADD CONSTRAINT `CONTA_ibfk_2` FOREIGN KEY (`Segundo_Corr`) REFERENCES `correntista` (`CPF`) ON UPDATE CASCADE,
  ADD CONSTRAINT `CONTA_ibfk_3` FOREIGN KEY (`Terceiro_Corr`) REFERENCES `correntista` (`CPF`) ON UPDATE CASCADE;

--
-- Limitadores para a tabela `transacao`
--
ALTER TABLE `transacao`
  ADD CONSTRAINT `TRANSACAO_ibfk_1` FOREIGN KEY (`Nro_Conta`) REFERENCES `conta` (`Numero`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `TRANSACAO_ibfk_2` FOREIGN KEY (`Nro_Conta_Transf`) REFERENCES `conta` (`Numero`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
