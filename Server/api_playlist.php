<?php
require "connect.php";
$sql = "SELECT DISTINCT * FROM playlist";
$data_playlist =mysqli_query($conn, $sql);

class Playlist {
    function Playlist($id_playlist, $name_playlist, $background_image_playlist, $icon_image_playlist) {
        $this->id_playlist = $id_playlist;
        $this->name_playlist = $name_playlist;
        $this->background_image_playlist = $background_image_playlist;
        $this->icon_image_playlist = $icon_image_playlist;
    }
}
$arr_playlist = array();
while ($row = mysqli_fetch_assoc($data_playlist)) {
    array_push($arr_playlist, new Playlist($row['id_playlist'], $row['name_playlist'], $row['background_image_playlist'], $row['icon_image_playlist']));
}

echo json_encode($arr_playlist);