package letv.zookeeper.monitor.dao;

import letv.zookeeper.monitor.model.ZkClusterInfo;

/**
 * zookeeper集群信息dao
 * @author wangdi5
 *
 */
public interface ZkClusterDao {

	public ZkClusterInfo getClusterInfo();
	
	public void updateClusterInfo(ZkClusterInfo zkClusterInfo);
	
	public int insertClusterInfo(ZkClusterInfo zkClusterInfo);
}
