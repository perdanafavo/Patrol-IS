<?php
include 'connection.php';
$i = 1;
 if (isset($_GET['id_patrol'])) {
   $db = Database::getInstance();
   $sql = $db->getConnection();
   $search = $_GET['id_patrol'];

   $query = "SELECT * FROM patrol WHERE ID_PATROL='$search'";
   $result = $sql->query($query);

   foreach ($result as $value) {
     if ($i==1) {
       echo json_encode($value);
       $i++;
     } else {
       echo ','.json_encode($value);
     }
   }
 }
