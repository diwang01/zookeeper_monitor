package letv.zookeeper.monitor.model;

import java.io.Serializable;
import java.util.Map;

/**
 * zookeeper服务器机器性能信息
 * @author wangdi5
 *
 */
public class HostPerformanceInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String ip;
	
	private String cpuUsage;
	
	private String load;
	
	private String memoryUsage;
	
	private Map< String/**挂载点*/, String/**使用百分比*/ > diskUsageMap;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(String memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public String getDiskUsageMap() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		
		for(Map.Entry<String, String> entry : this.diskUsageMap.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
		}
		
		String str = sb.toString();
		str = str.substring(0, str.length() - 1);
		
		return str + "}";
	}

	public void setDiskUsageMap(Map<String, String> diskUsageMap) {
		this.diskUsageMap = diskUsageMap;
	}
}
