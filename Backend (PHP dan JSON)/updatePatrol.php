<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();
$id = $_POST['id_patrol'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];
$Gambar = $_POST['Gambar'];

$result = $sql->query("UPDATE patrol
                        SET PICT='$Gambar', LATITUDE='$latitude', LONGITUDE='$longitude'
                        WHERE ID_PATROL=$id");

  if ($result) {
    $response = new usr();
	$response->success = 1;
    $response->message = 'berhasil ditambahkan';
    die(json_encode($response));
  } else {
    $response = new usr();
	$response->success = 0;
    $response->message = 'Terjadi Kesalahan';
    die(json_encode($response));
  }