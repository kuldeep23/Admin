    <?php

    // Include confi.php
    include_once('config.php');
    include_once('DBConnection.php');

    if($_SERVER['REQUEST_METHOD'] == "POST")
    {
           $name=$_POST['t1'];
           $design=$_POST['t2'];       
           $image = $_POST["image"];

            // Create a unique image name
            $image_name = "JHS-".bin2hex(random_bytes(5));

            $filename="http://localhost/gatesapi/images/".$image_name;

            $decodedImage = base64_decode("$image");
            $return = file_put_contents("images/" . $image_name . ".jpg", $decodedImage);
                
            $db=new DBConnection();
            $con=$db->OpenDBConnection();
            $qry="INSERT INTO `tbl_staff` (`id`, `name`, `desig`, `image`)
                      VALUES (NULL, '$name', '$design', '$filename')";

                $res=mysqli_query($con,$qry);
                
                    if($res)
                    {
                        $json = array("status" => 1, "msg" => "Entry Successfull");
                    }
                    else
                    {
                        $json = array("status" => 0, "msg" => $con->error);
                    }
    }
                else{
                    $json = array("status" => 0, "msg" => "Request method not accepted");       
                    }
        
    @mysqli_close($con);

    /* Output header */
        header('Content-type: application/json');
        echo json_encode($json);

    ?>
