<?php
include 'connection.php';
$i = 1; $j=1;
if (isset($_GET['iduser'])) {
  $db = Database::getInstance();
  $sql = $db->getConnection();
  $search = $_GET['iduser'];

  $query = "SELECT * FROM patrol WHERE ID_USER = $search ORDER BY ID_PATROL DESC";
  $result = $sql->query($query);

    $query = "SELECT * FROM activity";
    $results = $sql->query($query);

echo '{"patrol":[';

  foreach ($result as $value) {
    if ($i==1) {
      echo json_encode($value);
      $i++;
    } else {
      echo ','.json_encode($value);
    }
  }
  echo '],
    "log":[';
    
    foreach ($results as $value) {
    if ($j==1) {
      echo json_encode($value);
      $j++;
    } else {
      echo ','.json_encode($value);
    }
  }
    
    echo ']}';
 
}