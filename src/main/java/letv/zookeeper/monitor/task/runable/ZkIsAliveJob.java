package letv.zookeeper.monitor.task.runable;

import java.util.List;

import letv.zookeeper.monitor.constant.Constant;
import letv.zookeeper.monitor.constant.GlobalInstance;
import letv.zookeeper.monitor.exception.SSHException;
import letv.zookeeper.monitor.model.ZkModelInfo;
import letv.zookeeper.monitor.util.MessageUtil;
import letv.zookeeper.monitor.util.SSHUtil;

/**
 * 检查zookeeper是否存活
 * @author wangdi5
 *
 */
public class ZkIsAliveJob implements Runnable {

    private final List<String> phoneList;

    public ZkIsAliveJob(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public void run() {
        while (true) {
            for (ZkModelInfo model : GlobalInstance.zkModelinfoList) {
                //执行单个服务器命令，获取服务状态信息
                try {
                    if (!SSHUtil.sshZooKeeperAndHandleRuok(model)) {
                        for (String phoneNum : this.phoneList) {
                            MessageUtil.sendApiMessage(phoneNum,
                                    String.format(Constant.MESSAGE, model.getIp(), model.getPort()));
                        }
                    }
                } catch (SSHException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(Constant.THREAD_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
