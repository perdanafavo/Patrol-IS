<?php
include 'connection.php';
$i=1;
//class usr baru
class usr{}

//koneksi database
$db = Database::getInstance();
$sql = $db->getConnection();

//mengambil nilai method POST
$id = $_GET["id"];

//mengambil nilai dari database
$result = $sql->query("SELECT * FROM presensi WHERE ID_USER=$id ORDER BY ID_PRES DESC");
$row = $result->fetch_assoc();
//pengecekan kondisi
if (!empty($row)){
		$response = new usr();
		$response->success = true;
		$response->message = "Berhasil";
		$response->tanggal = $row['P_TGL'];
		die(json_encode($response));
	} else {
		$response = new usr();
		$response->success = false;
		$response->message = "Gagal";
		$response->id = null;
		$response->nama = null;
		die(json_encode($response));
	}
