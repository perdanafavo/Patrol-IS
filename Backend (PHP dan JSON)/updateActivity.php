<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$id = $_POST['id_patrol'];
$keterangan = $_POST['keterangan'];
$waktu = $_POST['waktu'];
$tanggal = $_POST['tanggal'];

$result = $sql->query(" UPDATE activity
                        SET KETERANGAN='$keterangan', WAKTU='$waktu', TANGGAL='$tanggal'
                        WHERE ID_PATROL=$id
  ");

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
