<div class="container">
    <div class="row">
        <div class="span12">
            <div class="widget">
            <p>${message}</p>
            	<form action="${base}/conf/update" method="post">
            	<input type="hidden" name="clusterId" value="${(zkClusterInfo.clusterId)!0}" />
                <table width="700px" border="1">
		 <tbody><tr style="background-color:#D3D1D1; text-align:center;" class="t3">
		 	<td><b>配置项</b></td>
		 	<td><b>设置参数</b></td>
		 </tr>
		 <tr class="t2">
		 	<td valign="middle">ZooKeeper集群名称</td>
		 	<td valign="middle"><input type="text" name="clusterName" id="clusterName" value="${(zkClusterInfo.clusterName)!""}" style="width:400px"></td>
		 </tr>
		 <tr class="t1">
		 	<td valign="middle">机器列表</td>
		 	<td valign="middle"><input type="text" name="serverList" id="serverList" value="${(zkClusterInfo.serverList)!""}" style="width:400px"></td>
		 </tr>
		 <tr class="t1">
		 	<td valign="middle">机器账号列表</td>
		 	<td valign="middle"><input type="text" name="serverAccount" id="serverAccount" value="${(zkClusterInfo.serverAccount)!""}" style="width:400px"></td>
		 </tr>
		 <tr class="t2">
		 	<td valign="middle">描述</td>
		 	<td valign="middle"><input type="text" name="description" id="description" value="${(zkClusterInfo.description)!""}" style="width:400px"></td>
		 </tr>
		 <tr class="t1">
		 	<td valign="middle"></td>
		 	<td align="right"><input type="submit" value="更新配置信息" size="90"> <font color="red"></font> </td>
		 </tr>
	</tbody></table></form>
	
			<table width="700px" border="1">
				 <tbody>
				 <tr style="background-color:#D3D1D1; text-align:center;" class="t3">
				 	<td colspan="2"><b>配置项说明</b></td>
				 </tr>
				 <tr class="t1">
				 	<td valign="middle">机器列表</td>
				 	<td valign="middle" style="width:540px">
					 	格式：hostName:clientPort,hostName:clientPort,hostName:clientPort</br>
					 	说明：hostName为服务器ip;clientPort为客户端连接 Zookeeper服务器的端口号</br>
					 	例如：127.0.0.1:3181,127.0.0.1:3182,127.0.0.1:3183
				 	</td>
				 </tr>
				 <tr class="t1">
				 	<td valign="middle">机器账号列表</td>
				 	<td valign="middle" style="width:500px">
				 		格式：hostName:user:password,hostName:user:password,hostName:user:password</br>
				 		说明：hostName为服务器ip;user、password为该服务器的登录账号</br>
				 		例如：10.154.250.38:root:root,10.154.250.38:root:root,10.154.250.38:root:root
				 	</td>
				 </tr>
			</tbody></table>
                
            </div>
        </div>
    </div>
</div>

