<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$id = $_POST['id'];
$state = $_POST['state'];

$result = $sql->query(" UPDATE patrol
                        SET STATE='$state'
                        WHERE ID_PATROL=$id
  ");

  if ($result) {
    $response = new usr();
    die(json_encode($response));
  } else {
    $response = new usr();
    die(json_encode($response));
  }
