<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$id_patrol = $_POST['idpatrol'];

$result = $sql->query("SELECT * FROM patrol WHERE ID_PATROL='$id_patrol'");
$row = $result->fetch_assoc();

if (!empty($row)){
		$response = new usr();
		$response->ID_PATROL = $row['ID_PATROL'];
		$response->ID_USER = $row['ID_USER'];
		$response->TGL = $row['TGL'];
		$response->WKT = $row['WKT'];
		$response->RUAS = $row['RUAS'];
		$response->ACT = $row['ACT'];
		$response->OBJECT = $row['OBJECT'];
		$response->NODE_A = $row['NODE_A'];
		$response->NODE_B = $row['NODE_B'];
		$response->LINK = $row['LINK'];
		$response->STATUS = $row['STATUS'];
		$response->LATITUDE = $row['LATITUDE'];
		$response->LONGITUDE = $row['LONGITUDE'];
		$response->PICT = $row['PICT'];
		$response->STATE = $row['STATE'];
		die(json_encode($response));
	} 
