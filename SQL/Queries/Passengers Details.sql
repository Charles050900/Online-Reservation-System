CREATE TABLE `passengerdetails` (
  `pnrnumber` varchar(10) NOT NULL,
  `passengername` varchar(45) NOT NULL,
  `passengerage` int NOT NULL,
  `phno` bigint NOT NULL,
  `gender` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `online_reservation`.`passengerdetails`
(`pnrnumber`,
`passengername`,
`passengerage`,
`phno`,
`gender`)
VALUES
(<{pnrnumber: }>,
<{passengername: }>,
<{passengerage: }>,
<{phno: }>,
<{gender: }>);
