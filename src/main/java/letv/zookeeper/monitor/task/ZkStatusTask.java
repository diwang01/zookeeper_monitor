package letv.zookeeper.monitor.task;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import letv.zookeeper.monitor.constant.GlobalInstance;
import letv.zookeeper.monitor.dao.ZkClusterDao;
import letv.zookeeper.monitor.model.ZkClusterInfo;
import letv.zookeeper.monitor.task.runable.HostPerformanceCollectJob;
import letv.zookeeper.monitor.task.runable.ZkIsAliveJob;
import letv.zookeeper.monitor.task.runable.ZkStatusCollectJob;
import letv.zookeeper.monitor.util.ThreadUtil;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * zookeeper服务器状态信息收集job
 * @author wangdi5
 *
 */
@Service
public class ZkStatusTask {

    @Autowired
    protected ZkClusterDao zkClusterDao;

    @Resource
    protected List<String> phoneList;

    @PostConstruct
    public void init() {

        if (CollectionUtils.isEmpty(GlobalInstance.zkModelinfoList)) {
            ZkClusterInfo zkClusterInfo = zkClusterDao.getClusterInfo();
            GlobalInstance.resetZkModelinfoList(zkClusterInfo);
        }

        this.collectZkStatusInfo();
    }

    /**
     * 收集zookeeper服务器状态信息
     */
    private void collectZkStatusInfo() {
        ThreadUtil.submit(new ZkStatusCollectJob());
        ThreadUtil.submit(new ZkIsAliveJob(this.phoneList));
        ThreadUtil.submit(new HostPerformanceCollectJob());
    }
}
