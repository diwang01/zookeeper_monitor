<div class="container">
    <div class="row">
        <div class="span12">
            <div class="widget">
            <p>更新时期：${dateStrHostPerformance}</p>
            <table border="1" style="background-color:#D3D1D1; text-align:center;" class="t3">
				<tbody>
				<tr style="background-color:#DDDDDE;" class="">
					<td><b>服务 IP</b></td>
					<td><b>Cpu Usage</b></td>
					<td><b>Memory Usage</b></td>
					<td><b>Load</b></td>
					<td><b>Disk</b></td>
				</tr>
				
				<#list hostPerformanceInfoList as hostPerformanceInfo>
				<tr class="">
					<td>${(hostPerformanceInfo.ip)!""}</td>
					<td>${(hostPerformanceInfo.cpuUsage)!""}</td>
					<td>${(hostPerformanceInfo.memoryUsage)!""}</td>
					<td>${(hostPerformanceInfo.load)!""}</td>
					<td>${(hostPerformanceInfo.diskUsageMap)!""}</td>
				</tr>
				</#list>
			</tbody></table>
                
            </div>
        </div>
    </div>
</div>

