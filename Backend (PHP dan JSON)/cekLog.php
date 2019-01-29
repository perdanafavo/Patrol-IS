<?php
include 'connection.php';
$i = 1;
  $db = Database::getInstance();
  $sql = $db->getConnection();

  $query = "SELECT * FROM activity";
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