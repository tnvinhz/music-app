<?php
require "connect.php";

class Category {
    function Category($id_category, $name_category, $image_category) {
        $this->id_category = $id_category;
        $this->name_category = $name_category;
        $this->image_category = $image_category;
    }
}

$sql = "SELECT * FROM category ";
$data_category =mysqli_query($conn, $sql);

$arr_category = array();
while($row = mysqli_fetch_assoc($data_category)) {
    array_push($arr_category, new Category($row['id_category'], $row['name_category'], $row['image_category']));
}

echo json_encode($arr_category);
