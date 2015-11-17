package letv.zookeeper.monitor.task.runable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import letv.zookeeper.monitor.constant.Constant;
import letv.zookeeper.monitor.constant.GlobalInstance;
import letv.zookeeper.monitor.exception.SSHException;
import letv.zookeeper.monitor.model.ZkModelInfo;
import letv.zookeeper.monitor.model.ZkStatusInfo;
import letv.zookeeper.monitor.util.DateUtil;
import letv.zookeeper.monitor.util.SSHUtil;

/**
 * 监控zookeeper服务器状态信息job
 * @author wangdi5
 *
 */
public class ZkStatusCollectJob implements Runnable {

	@Override
	public void run() {
		while(true) {
			List<ZkStatusInfo> list = new ArrayList<ZkStatusInfo>();
			
			for(ZkModelInfo model : GlobalInstance.zkModelinfoList) {
				ZkStatusInfo zkStatus = new ZkStatusInfo();
				zkStatus.setIp(model.getIp());
				
				//执行单个服务器命令，获取服务状态信息
				try {
					SSHUtil.sshBatchZookeeperState(model, zkStatus);
				} catch (SSHException e) {
					e.printStackTrace();
				}
				
				list.add(zkStatus);
			}
			
			GlobalInstance.resetZkStatusInfoList(list);
			
			GlobalInstance.dateStrZkStatus = DateUtil.formatTime(new Date());
			
			try {
				Thread.sleep(Constant.THREAD_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
