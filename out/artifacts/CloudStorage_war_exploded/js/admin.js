function showDetail(nodename)
{
	$.ajax({	
		data:"nodename="+nodename,
		url:"nodeInfo/nodeInfo",
		success:function(data){  
			$(".file-info-container").html(data);
			$("#detail-info-myModal").modal("show");	
		},
	
	     error:function(data){ 
	    	 alert("≥ˆ¥Ì£¨«Î…‘∫Û÷ÿ ‘");
	     },
	});
}