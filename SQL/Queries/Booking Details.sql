CREATE TABLE `bookingdetails` (
  `pnrnumber` varchar(10) DEFAULT NULL,
  `countoftickets` int DEFAULT NULL,
  `trainname` varchar(50) DEFAULT NULL,
  `departuretime` time DEFAULT NULL,
  `departure` varchar(50) DEFAULT NULL,
  `classtype` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `online_reservation`.`bookingdetails`
(`pnrnumber`,
`countoftickets`,
`trainname`,
`departuretime`,
`departure`,
`classtype`)
VALUES
(<{pnrnumber: }>,
<{countoftickets: }>,
<{trainname: }>,
<{departuretime: }>,
<{departure: }>,
<{classtype: }>);
