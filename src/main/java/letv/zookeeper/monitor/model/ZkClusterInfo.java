package letv.zookeeper.monitor.model;

import java.io.Serializable;

/**
 * zookeeper集群信息
 * @author wangdi5
 *
 */
public class ZkClusterInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int clusterId;
	
	private String clusterName;
	
	private String serverList;
	
	private String serverAccount;
	
	private String description;

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getServerList() {
		return serverList;
	}

	public void setServerList(String serverList) {
		this.serverList = serverList;
	}

	public String getServerAccount() {
		return serverAccount;
	}

	public void setServerAccount(String serverAccount) {
		this.serverAccount = serverAccount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
