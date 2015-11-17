package letv.zookeeper.monitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import letv.zookeeper.monitor.constant.Constant;
import letv.zookeeper.monitor.exception.SSHException;
import letv.zookeeper.monitor.model.HostPerformanceInfo;
import letv.zookeeper.monitor.model.ZkModelInfo;
import letv.zookeeper.monitor.model.ZkStatusInfo;

import org.apache.commons.lang3.StringUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 链接linux工具类
 * @author wangdi5
 *
 */
public class SSHUtil {
	
	private static final String RECEIVED = "Received: ";
	
	private static final String SENT = "Sent: ";
	
	private static final String CONNECTIONS = "Connections: ";
	
	private static final String MODE_TYPE = "Mode: ";
	
	private static final String NODE_COUNT = "Node count: ";
	
	private static final String CONNECTIONS_WATCHING = "connections watching";
	
	private static final String TOTAL_WATCHES = "Total watches:";
	
	private static final String AU_OK = "imok";
	
	private static final String EMPTY_STR = "";
	
	private final static String LOAD_AVERAGE_STRING = "load average: ";
	
	private final static String CPU_USAGE_STRING = "Cpu(s):";
	
	private final static String MEM_USAGE_STRING = "Mem:";
	
	private final static String SWAP_USAGE_STRING = "Swap:";
	
	public final static String PERCENT = "%";
	
	/**
	 * 进行Telnet链接并进行返回处理，获取zookeeper服务机器的性能信息 df
	 * @param zkModelInfo
	 * @param hostPerformanceInfo
	 * @throws SSHException
	 */
	public static void sshHostPerformanceDiskInfo(ZkModelInfo zkModelInfo, 
			HostPerformanceInfo hostPerformanceInfo) throws SSHException {
		String result = SSHUtil.execute(zkModelInfo.getIp(), zkModelInfo.getUser(), 
				zkModelInfo.getPwd(), zkModelInfo.getCmdByDf());
		
		if(StringUtils.isBlank(result)) {
			return ;
		}
		
		//统计磁盘使用状况
		Map<String, String> diskUsageMap = new HashMap<String, String>();
		
		String[] resultArry = result.split(Constant.BR);
		
		for(String str : resultArry) {
			if(StringUtils.isBlank(str)) {
				continue;
			}
			
			str = str.replaceAll(" {1,}", " ");
			String[] lineArray = str.split(" ");
			if ( 6 != lineArray.length ) {
				continue;
			}
			String diskUsage = lineArray[4];
			String mountedOn = lineArray[5];
			diskUsageMap.put(mountedOn, diskUsage);
		}
		
		hostPerformanceInfo.setDiskUsageMap(diskUsageMap);
	}
	
	/**
	 * 进行Telnet链接并进行返回处理，获取zookeeper服务机器的性能信息 top
	 * @param zkModelInfo
	 * @param hostPerformanceInfo
	 */
	public static void sshHostPerformanceInfo(ZkModelInfo zkModelInfo, 
			HostPerformanceInfo hostPerformanceInfo) throws SSHException {
		String result = SSHUtil.execute(zkModelInfo.getIp(), zkModelInfo.getUser(), 
				zkModelInfo.getPwd(), zkModelInfo.getCmdByTop());
		
		if(StringUtils.isBlank(result)) {
			return ;
		}
		
		String[] resultArry = result.split(Constant.BR);
		
		String totalMem = EMPTY_STR;
		String freeMem = EMPTY_STR;
		String buffersMem = EMPTY_STR;
		String cachedMem = EMPTY_STR;
		
		for(String str : resultArry) {
			if(StringUtils.isBlank(str)) {
				continue;
			}
			
			if(str.contains(LOAD_AVERAGE_STRING)) {
				// 第一行，通常是这样：
				// top - 19:58:52 up 416 days, 30 min, 1 user, load average:
				// 0.00, 0.00, 0.00
				int loadAverageIndex = str.indexOf(LOAD_AVERAGE_STRING);
				String loadAverages = str.substring(loadAverageIndex).replace(LOAD_AVERAGE_STRING, EMPTY_STR);
				hostPerformanceInfo.setLoad(loadAverages);
			} else if(str.contains(CPU_USAGE_STRING)) {
				// 第三行通常是这样：
				// Cpu(s): 0.0% us, 0.0% sy, 0.0% ni, 100.0% id, 0.0% wa,
				// 0.0% hi, 0.0% si
				String cpuUsage = str.split(",")[0].replace(CPU_USAGE_STRING, EMPTY_STR).replace( "us", EMPTY_STR);
				hostPerformanceInfo.setCpuUsage(cpuUsage);
			} else if(str.contains(MEM_USAGE_STRING)) {
				// 第四行通常是这样：
				// Mem: 1572988k total, 1490452k used, 82536k free, 138300k
				// buffers
				String[] memArray = str.replace(MEM_USAGE_STRING, EMPTY_STR).split(",");
				totalMem = memArray[0].replace("total", EMPTY_STR).replace("k", EMPTY_STR);;
				freeMem = memArray[2].replace("free", EMPTY_STR).replace("k", EMPTY_STR);
				buffersMem = memArray[3].replace("buffers", EMPTY_STR).replace("k", EMPTY_STR);
			} else if(str.contains(SWAP_USAGE_STRING)) {
				// 第四行通常是这样：
				// Swap: 2096472k total, 252k used, 2096220k free, 788540k
				// cached
				String[] memArray = str.replace(SWAP_USAGE_STRING, EMPTY_STR).split(",");
				cachedMem = memArray[3].replace("cached", EMPTY_STR ).replace("k", EMPTY_STR);

				Double totalMemDouble = Double.parseDouble(totalMem);
				Double freeMemDouble = Double.parseDouble(freeMem);
				Double buffersMemDouble = Double.parseDouble(buffersMem);
				Double cachedMemDouble = Double.parseDouble(cachedMem);

				Double memoryUsage = 1 - ((freeMemDouble + buffersMemDouble + cachedMemDouble) / totalMemDouble);
				hostPerformanceInfo.setMemoryUsage( memoryUsage * 100 + PERCENT );
			}
		}
	}
	
	/**
	 * 进行Telnet链接并进行返回处理，批量执行zk命令获取zk状态
	 * @param zkModelInfo
	 * @param zkStatus
	 */
	public static void sshBatchZookeeperState(ZkModelInfo zkModelInfo, ZkStatusInfo zkStatus) throws SSHException {
		String result = SSHUtil.execute(zkModelInfo.getIp(), zkModelInfo.getUser(), 
				zkModelInfo.getPwd(), zkModelInfo.getBatchCmd());
		
		if(StringUtils.isBlank(result)) {
			return ;
		}
		
		String[] resultArry = result.split(Constant.BR);
		
		for(String str : resultArry) {
			//ruok
			if(result.contains(AU_OK)) {
				zkStatus.setWorkStatus(0);
			} else {
				zkStatus.setWorkStatus(1);
			}
			
			//wchs
			if(str.contains(CONNECTIONS_WATCHING)) {
				zkStatus.setWatchedDes(str);
			} else if(str.contains(TOTAL_WATCHES)) {
				zkStatus.setTotalWatches(str.replaceFirst(TOTAL_WATCHES, EMPTY_STR));
			}
			
			//stat
			if(str.contains(RECEIVED)) {
				zkStatus.setReceived(str.replaceFirst(RECEIVED, EMPTY_STR));
			} else if(str.contains(SENT)) {
				zkStatus.setSent(str.replaceFirst(SENT, EMPTY_STR));
			} else if(str.contains(CONNECTIONS)) {
				zkStatus.setConnections(str.replaceFirst(CONNECTIONS, EMPTY_STR));
			} else if(str.contains(NODE_COUNT)) {
				zkStatus.setNodeCount(str.replaceFirst(NODE_COUNT, EMPTY_STR));
			} else if(str.contains(MODE_TYPE)) {
				zkStatus.setMode(str.replaceFirst(MODE_TYPE, EMPTY_STR));
			}
			
		}
	}
	
	/**
	 * 进行Telnet连接并进行返回处理，执行 zk的ruok命令
	 * @param zkModelInfo
	 * @return
	 * @throws SSHException
	 */
	public static boolean sshZooKeeperAndHandleRuok(ZkModelInfo zkModelInfo) throws SSHException  {
		String result = SSHUtil.execute(zkModelInfo.getIp(), zkModelInfo.getUser(), 
				zkModelInfo.getPwd(), zkModelInfo.getCmdByRuok());
		
		if(result.contains(AU_OK)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 链接linux执行shell语句
	 * @param hostname
	 * @param user
	 * @param pwd
	 * @param cmd
	 * @return
	 */
	public static String execute(String hostName, String user, String pwd, String cmd) throws SSHException {
		Connection conn = null;
		StringBuffer sb = null;
		Session session = null;
		BufferedReader bf = null;
		
		if(StringUtils.isBlank(cmd)) {
			return Constant.EMPTY_STRING;
		}
		
		try {
			conn = new Connection(hostName);
			conn.connect();

			if(conn.authenticateWithPassword(user, pwd)) {
				sb = new StringBuffer();
				session = conn.openSession();
				session.execCommand(cmd);
				bf = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));
				String line = "";
				while((line = bf.readLine()) != null) {
					sb.append(line).append(Constant.BR);
				}
				
				return sb.toString();
			} else {
				throw new SSHException(String.format("SSH authentication failed with [userName=%s, password=%s]", user, pwd));
			}
		} catch (IOException e) {
			throw new SSHException(String.format("SSH failed, [cmd=%s,userName=%s,password=%s], error=%s", cmd, user, pwd, e.getMessage()));
		} finally {
			if(bf != null) {
				try {
					bf.close();
				} catch (IOException e) {
				}
			}
			
			if(session != null) {
				session.close();
			}
			
			if(conn != null) {
				conn.close();
			}
		}
	}
}
