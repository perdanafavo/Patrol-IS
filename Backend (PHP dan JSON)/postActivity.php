<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$id = $_POST['id'];
$keterangan = $_POST['keterangan'];
$waktu = $_POST['waktu'];
$tanggal = $_POST['tanggal'];

$result = $sql->query(" INSERT INTO activity
                        VALUES (null, $id, '$keterangan', '$waktu', '$tanggal')
  ");

if ($result) {
  $response = new usr();
  die(json_encode($response));
} else {
  $response = new usr();
  die(json_encode($response));
}
