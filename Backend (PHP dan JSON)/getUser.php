<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$username = $_POST['username'];
$password = $_POST['password'];

$result = $sql->query("SELECT * FROM user WHERE USERNAME='$username'");
$row = $result->fetch_assoc();

if (!empty($row)){
		$response = new usr();
		$response->success = true;
		$response->message = "Selamat datang ".$row['NAMA'];
		$response->id = $row['ID_USER'];
		$response->nama = $row['NAMA'];
		$response->password = password_verify ( $password, $row['PASSWORD'] );
		die(json_encode($response));
	} else {
		$response = new usr();
		$response->success = false;
		$response->message = "Username atau password salah";
		$response->id = null;
		$response->nama = null;
		die(json_encode($response));
	}
