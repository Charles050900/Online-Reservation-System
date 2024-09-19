CREATE TABLE `traindetails` (
  `trainno` int DEFAULT NULL,
  `trainname` varchar(50) DEFAULT NULL,
  `departuretime` time DEFAULT NULL,
  `departure` varchar(50) DEFAULT NULL,
  `arrival` varchar(50) DEFAULT NULL,
  `trainstatus` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `online_reservation`.`traindetails`
(`trainno`,
`trainname`,
`departuretime`,
`departure`,
`arrival`,
`trainstatus`)
VALUES
(<{trainno: }>,
<{trainname: }>,
<{departuretime: }>,
<{departure: }>,
<{arrival: }>,
<{trainstatus: }>);
