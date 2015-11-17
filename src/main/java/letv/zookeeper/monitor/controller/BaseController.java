package letv.zookeeper.monitor.controller;

import letv.zookeeper.monitor.constant.GlobalInstance;
import letv.zookeeper.monitor.dao.ZkClusterDao;
import letv.zookeeper.monitor.model.ZkClusterInfo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	@Autowired
	protected ZkClusterDao zkClusterDao;
	
	/**
	 * 获取zookeeper客户端实例
	 * @return
	 */
	protected CuratorFramework getZkClient() {
		if(GlobalInstance.client == null) {
			synchronized (this) {
				if(GlobalInstance.client == null) {
					ZkClusterInfo info = this.zkClusterDao.getClusterInfo();
					GlobalInstance.client = CuratorFrameworkFactory.builder().retryPolicy(new ExponentialBackoffRetry(1000, 10))
						.connectString(info.getServerList()).connectionTimeoutMs(5000).build();
					GlobalInstance.client.start();
				}
			}
		}
		
		return GlobalInstance.client;
	}
	
	/**
	 * 重置zookeeper配置信息
	 */
	protected void resetZkClient() {
		if(GlobalInstance.client != null) {
			GlobalInstance.client.close();
			GlobalInstance.client = null;
		}
	}
	
	/**
	 * 重置zkModelInfoList信息
	 */
	protected void resetZkModelinfoList() {
		ZkClusterInfo zkClusterInfo = zkClusterDao.getClusterInfo();
		GlobalInstance.resetZkModelinfoList(zkClusterInfo);
	}
}
