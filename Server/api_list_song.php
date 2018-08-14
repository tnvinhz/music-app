<?php
require "connect.php";

$id_playlist = "1";
$query_id_playlist = "SELECT * FROM playlist WHERE id_playlist = '$id_playlist' ";
$data_id_playlist =mysqli_query($conn, $query_id_playlist);
$row_playlist = mysqli_fetch_assoc($data_id_playlist);
