<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$id = $_POST['id_activity'];

$result = $sql->query("DELETE FROM activity WHERE ID_ACT='$id'");

if ($result) {
  $response = new usr();
	$response->success = 1;
  $response->message = 'berhasil dihapus';
  die(json_encode($response));
} else {
  $response = new usr();
	$response->success = 0;
  $response->message = 'Terjadi Kesalahan';
  die(json_encode($response));
}
