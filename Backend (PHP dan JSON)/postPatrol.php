<?php
include 'connection.php';

class usr{}

$db = Database::getInstance();
$sql = $db->getConnection();

$id = $_POST['id'];
$activity = $_POST['activity'];
$object = $_POST['object'];
$nodeA = $_POST['nodeA'];
$nodeB = $_POST['nodeB'];
$link = $_POST['link'];
$status = $_POST['status'];

$result = $sql->query("
  UPDATE patrol
  SET ACT='$activity', OBJECT='$object', NODE_A='$nodeA', NODE_B='$nodeB', LINK='$link', STATUS='$status'
  WHERE ID_PATROL = $id
  ");

  if ($result) {
    $response = new usr();
    die(json_encode($response));
  } else {
    $response = new usr();
    die(json_encode($response));
  }
