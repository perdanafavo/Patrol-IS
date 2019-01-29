<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$id = $_POST['id'];
$tanggal = $_POST['tanggal'];
$waktu = $_POST['waktu'];

$result = $sql->query(" INSERT INTO presensi
                        VALUES (null, $id, '$tanggal','$waktu')
  ");

if ($result) {
$response = new usr();
$response->success = true;
$response->message = 'berhasil ditambahkan';
die(json_encode($response));
} 
else {
$response = new usr();
$response->success = false;
$response->message = 'Terjadi Kesalahan';
die(json_encode($response));
}
