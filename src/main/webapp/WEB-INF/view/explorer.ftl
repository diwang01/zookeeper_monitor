<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="jquery,ui,easy,easyui,web">
	<meta name="description" content="easyui help you build your web page easily!">
	<title>jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="${base}/resources/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/resources/themes/icon.css">
	<script src="${base}/resources/javascripts/jquery.min.js"></script>
	<script src="${base}/resources/javascripts/jquery.easyui.min.js"></script>
</head>
<body>
	<div id="main"> 
	    <div id="left" style="float:left;padding:10px;width:200px">
			<ul id="root"></ul>
	    </div> 
	    <div id="right" style="float:left;padding:10px;width:500px">
	    	<div id="showDataDiv" style="display:none">
	    		<div id="showData" align="center"></div>
	    		<div align="center">
		    		<input id="nodePath" type="hidden" value="" size="90">
		    		<input onclick="deleteNode()" type="button" value="删除节点" size="90">
		    	</div>
	    	</div>
	    </div> 
	</div>

	<script type="text/javascript">
	<!--
		$('#root').tree({
			url:'${base}/getPath?id=00'
		});
		
		$('#root').tree({
		    onClick: function(node){
		    	$("#showData").text("");
		    	$("#nodePath").attr("value", '');
		    	$("#showDataDiv").hide();
		    	
	        	if(node.attributes) {
	        		$.ajax({
		            type: "POST",
		            url: "${base}/getData",
		            data: "path=" + node.attributes.url,             
		            dataType: "json", 
		            success: function (result) {
		            	$("#showData").text("节点信息值为：" + result.data);  
		            	$("#nodePath").attr("value", node.attributes.url);
		            	$("#showDataDiv").show();
		            },
	
		            error: function (result) { 
		               alert("错误");
		            } 
	    		});
	    	}
		  },
		  
		  onCollapse: function(node) {
		  	var childrens = $('#root').tree('getChildren', node.target);
		  	$(node.target).nextAll("ul").remove();
		  }
		});
		
		//删除节点信息
		function deleteNode() {
			if(confirm("确定要删除数据吗")){
				var pathValue = $("#nodePath").val();
			
				$.ajax({
		            type: "POST",
		            url: "${base}/delData",
		            data: "path=" + pathValue,             
		            dataType: "json", 
		            success: function (result) {
		            	if(result.flag == true) {
		            		alert("删除成功");
		            		window.location.href="explorer";
		            	} else {
		            		alert("删除失败");
		            	}
		            },
	
		            error: function (result) { 
		               alert("错误");
		            } 
	    		});
			}
		}
    //-->
	</script>
</body>
</html>