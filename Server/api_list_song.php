<?php
require "connect.php";

class ListSong {
    function ListSong($id_song, $id_album, $id_category, $id_playlist, $name_song, $image_song, $singer_song, $link_song)
    {
        $this->id_song = $id_song;
        $this->id_album = $id_album;
        $this->id_category = $id_category;
        $this->id_playlist = $id_playlist;
        $this->name_song = $name_song;
        $this->image_song = $image_song;
        $this->singer_song = $singer_song;
        $this->link_song = $link_song;
    }
}
$arr_list_song = array();
if(isset($_POST['id_playlist'])) {
    $id_playlist = $_POST['id_playlist'];
    $query = "SELECT * FROM song WHERE FIND_IN_SET('$id_playlist', id_playlist) ";
}

$data_songs = mysqli_query($conn, $query);

while ($row = mysqli_fetch_assoc($data_songs)) {
    array_push($arr_list_song, new ListSong($row['id_song'], $row['id_album'], $row['id_category'], $row['id_playlist'], $row['name_song'], $row['image_song'], $row['singer_song'], $row['link_song']));
}
echo json_encode($arr_list_song);