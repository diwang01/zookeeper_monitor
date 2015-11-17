package letv.zookeeper.monitor.model;

import java.io.Serializable;



/**
 * 单个zookeeper服务器的状态
 * @author wangdi5
 *
 */
public class ZkStatusInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ip;
	
	private String received;
	
	private String sent;
	
	private String connections;
	
	private String mode;
	
	private String nodeCount;
	
	private String watchedDes;
	
	private String totalWatches;
	
	//0表示正常；1表示不正常
	private int workStatus = 1;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getSent() {
		return sent;
	}

	public void setSent(String sent) {
		this.sent = sent;
	}

	public String getConnections() {
		return connections;
	}

	public void setConnections(String connections) {
		this.connections = connections;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(String nodeCount) {
		this.nodeCount = nodeCount;
	}

	public String getWatchedDes() {
		return watchedDes;
	}

	public void setWatchedDes(String watchedDes) {
		this.watchedDes = watchedDes;
	}

	public String getTotalWatches() {
		return totalWatches;
	}

	public void setTotalWatches(String totalWatches) {
		this.totalWatches = totalWatches;
	}

	public int getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
	}
}
