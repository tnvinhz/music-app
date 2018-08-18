<?php
    $host_name = "localhost";
    $username = "root";
    $password = "thuynt1104";
    $database_name = "musicapp";

    $conn = mysqli_connect($host_name, $username, $password, $database_name);
    mysqli_set_charset($conn,"utf8");
     if (!mysqli_set_charset($con, "utf8")) {
     } else {
         echo "Not connect to database!";
     }