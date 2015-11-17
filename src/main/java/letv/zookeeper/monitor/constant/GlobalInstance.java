package letv.zookeeper.monitor.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import letv.zookeeper.monitor.model.HostPerformanceInfo;
import letv.zookeeper.monitor.model.ZkClusterInfo;
import letv.zookeeper.monitor.model.ZkModelInfo;
import letv.zookeeper.monitor.model.ZkStatusInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;

/**
 * 全局静态实例对象
 * @author wangdi5
 *
 */
public class GlobalInstance {

	public static CuratorFramework client;
	
	public static List<ZkModelInfo> zkModelinfoList = new CopyOnWriteArrayList<ZkModelInfo>();
	
	public static List<ZkStatusInfo> zkStatusList = new CopyOnWriteArrayList<ZkStatusInfo>();
	
	public static List<HostPerformanceInfo> hostPerformanceInfoList = new CopyOnWriteArrayList<HostPerformanceInfo>();
	
	public static String dateStrZkStatus;
	
	public static String dateStrHostPerformance;
	
	/**
	 * 重置HostPerformanceInfo信息
	 * @param list
	 */
	public static void resetHostPerformanceInfoList(List<HostPerformanceInfo> list) {
		synchronized (GlobalInstance.class) {
			hostPerformanceInfoList.clear();
			hostPerformanceInfoList.addAll(list);
		}
	}
	
	/**
	 * 重置ZkStatusInfo信息
	 * @param zkStatusList
	 */
	public static void resetZkStatusInfoList(List<ZkStatusInfo> list) {
		synchronized (GlobalInstance.class) {
			zkStatusList.clear();
			zkStatusList.addAll(list);
		}
	}
	
	/**
	 * 重置zkModelInfoList信息
	 */
	public static void resetZkModelinfoList(ZkClusterInfo zkClusterInfo) {
		String serverListStr = zkClusterInfo.getServerList();
		String serverAccountStr = zkClusterInfo.getServerAccount();
		
		if(StringUtils.isBlank(serverListStr) || StringUtils.isBlank(serverAccountStr)) {
			return ;
		}
		
		List<ZkModelInfo> list = new ArrayList<ZkModelInfo>();
		String[] serverListArry = serverListStr.split(",");
		String[] serverAccountArry = serverAccountStr.split(",");
		
		for(String server : serverListArry) {
			if(StringUtils.isNotBlank(server) && server.split(":").length == 2) {
				String[] serverArry = server.split(":");
				ZkModelInfo model = new ZkModelInfo();
				//获取单个服务器的基本信息
				model.setIp(serverArry[0]);
				model.setPort(serverArry[1]);
				
				for(String account : serverAccountArry) {
					if(StringUtils.isNotBlank(account) && account.split(":").length == 3) {
						String[] accountArry = account.split(":");
						if(model.getIp().equals(accountArry[0])) {
							//获取单个服务器的基本信息
							model.setUser(accountArry[1]);
							model.setPwd(accountArry[2]);
						}
					}
				}
				
				list.add(model);
			}
		}
		
		//设值服务器基本信息到全局list中
		if(CollectionUtils.isNotEmpty(list)) {
			synchronized (GlobalInstance.class) {
				zkModelinfoList.clear();
				zkModelinfoList.addAll(list);
			}
		}
	}
}
