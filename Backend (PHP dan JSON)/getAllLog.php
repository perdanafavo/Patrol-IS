<?php
include 'connection.php';
$i = 1;
if (isset($_GET['idpatrol'])) {
  $db = Database::getInstance();
  $sql = $db->getConnection();
  $search = $_GET['idpatrol'];

  $query = "SELECT * FROM activity WHERE ID_PATROL='$search'";
  $result = $sql->query($query);

echo '{"activity":[';

  foreach ($result as $value) {
    if ($i==1) {
      echo json_encode($value);
      $i++;
    } else {
      echo ','.json_encode($value);
    }
  }
  echo ']}';
 
}