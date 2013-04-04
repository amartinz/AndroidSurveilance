//base = '/alex/admin/';
base = '/androidcp/admin/';

function getXMLHttp()
{
  var xmlHttp

  try
  {
    //Firefox, Opera 8.0+, Safari
    xmlHttp = new XMLHttpRequest();
  }
  catch(e)
  {
    //Internet Explorer
    try
    {
      xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch(e)
    {
      try
      {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
      }
      catch(e)
      {
        alert("Your browser does not support AJAX!")
        return false;
      }
    }
  }
  return xmlHttp;
}


function updatecontent(ct)
{
	var checked = false;
	var chkbox = document.getElementsByName("device");
	var allbox = document.getElementById("all").checked;
	var select = document.getElementById("selectbox").value;
	var cmd = document.getElementById("cmd").value;
	var arr;

	ct=ct-1;

	if(allbox){
		arr = "all";
		checked=true;
	}else{
		for (var i = 0; i < ct; i++) {
			if (chkbox[i].checked) {
				if(arr=="" || arr == null){
					arr = chkbox[i].value;
				}else{
					arr = arr + "'*'" + chkbox[i].value;
				}
				checked = true;
			};
		};
	}

	if(select=="sendcmd"){
		if(cmd=="" || cmd == null){
			alert("Please enter the comamnd");
			return;
		}else{
			data = [arr,cmd];
		}
	}else{
		data = [arr];
	}

	//alert(JSON.stringify(data));

	if(checked){
		var xmlHttp = new getXMLHttp();

	  xmlHttp.onreadystatechange = function()
	  {
	    if(xmlHttp.readyState == 4 && xmlHttp.status==200)
	    { 
	      document.getElementById("content").innerHTML = xmlHttp.responseText;
	    }
	  };

	  var str= base + 'updatecontent/' + select +'/';
	  xmlHttp.open("POST",str,false);
	  xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded/json');
	  xmlHttp.send(JSON.stringify(data));
	} 
}

function editcmd(ids)
{
  var xmlHttp = new getXMLHttp();
  xmlHttp.onreadystatechange = function()
  {
    if(xmlHttp.readyState == 4 && xmlHttp.status==200)
    {
      document.getElementById("div_cmd").innerHTML = xmlHttp.responseText;
    }
  };

  var str= base + 'editcmd/' + ids;
  xmlHttp.open("GET",str, true); 
  xmlHttp.send(null);
}

function updatecmd(ids,data)
{
  var xmlHttp = new getXMLHttp();
  var arr = [data['clm_cmdname'],data['clm_cmdvalue']];
  
  xmlHttp.onreadystatechange = function()
  {
    if(xmlHttp.readyState == 4 && xmlHttp.status==200)
    { 
      document.getElementById("div_cmd").innerHTML = xmlHttp.responseText;
    }
  };

  var str= base + 'updatecmd/' + ids + '/';
  xmlHttp.open("POST",str,false);
  xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded/json');
  xmlHttp.send(JSON.stringify(arr)); 
}

function deletecmd(ids)
{
  var xmlHttp = new getXMLHttp();
  
  xmlHttp.onreadystatechange = function()
  {
    if(xmlHttp.readyState == 4 && xmlHttp.status==200)
    { 
      document.getElementById("div_cmd").innerHTML = xmlHttp.responseText;
    }
  };

  var str= base + 'deletecmd/' + ids;
  xmlHttp.open("GET",str, true); 
  xmlHttp.send(null); 
}