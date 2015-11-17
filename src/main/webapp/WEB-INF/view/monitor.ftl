<div class="container">
    <div class="row">
        <div class="span12">
            <div class="widget">
            <p>更新时期：${dateStrZkStatus}</p>
            <table border="1" style="background-color:#D3D1D1; text-align:center;" class="t3">
				<tbody>
				<tr style="background-color:#DDDDDE;" class="">
					<td><b>服务 IP</b></td>
					<td><b>Role</b></td>
					<td><b>连接数</b></td>
					<td><b>Watch描述</b></td>
					<td><b>Total watches</b></td>
					<td><b>数据量 Sent/Received</b></td>
					<td><b>服务自检状态</b></td>
				</tr>
				
				<#list zkStatusList as zkStatus>
				<tr class="">
					<td>${(zkStatus.ip)!""}</td>
					<td>${(zkStatus.mode)!""}</td>
					<td>${(zkStatus.connections)!""}</td>
					<td>${(zkStatus.watchedDes)!""}</td>
					<td>${(zkStatus.totalWatches)!""}</td>
					<td>${(zkStatus.sent)!""}/${(zkStatus.received)!""}</td>
					<td><#if zkStatus.workStatus == 0> 正常工作 </#if><#if zkStatus.workStatus == 1> <font color="red">未正常工作</font> </#if></td>
				</tr>
				</#list>
			</tbody></table>
                
            </div>
        </div>
    </div>
</div>

