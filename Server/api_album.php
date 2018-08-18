<?php
   require "connect.php";
    $sql = "SELECT DISTINCT * FROM album ORDER BY rand(". date('Y-m-d').") LIMIT 10";
    $data_album =mysqli_query($conn, $sql);

    class Album {
        function Album($id_album, $name_album, $singer_name_album, $image_album) {
            $this->id_album = $id_album;
            $this->name_album = $name_album;
            $this->singer_name_album = $singer_name_album;
            $this->image_album = $image_album;
        }
    }
    $arr_album = array();
    while ($row = mysqli_fetch_assoc($data_album)) {
        array_push($arr_album, new Album($row['id_album'], $row['name_album'], $row['singer_name_album'], $row['image_album']));
    }

    echo json_encode($arr_album);


?>