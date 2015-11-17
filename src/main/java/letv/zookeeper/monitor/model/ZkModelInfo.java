package letv.zookeeper.monitor.model;

import java.io.Serializable;

import letv.zookeeper.monitor.constant.Constant;

/**
 * 单个zookeeper信息
 * @author wangdi5
 *
 */
public class ZkModelInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ip;
	
	private String user;
	
	private String pwd;
	
	private String port;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * 获取df命令
	 * @return
	 */
	public String getCmdByDf() {
		return Constant.CMD_DF_LH;
	}
	
	/**
	 * 获取top命令
	 * @return
	 */
	public String getCmdByTop() {
		return Constant.CMD_TOP;
	}
	
	/**
	 * 获取ruok命令串
	 * @return
	 */
	public String getCmdByRuok() {
		return String.format(Constant.CMD_RUOK, this.getIp(), this.getPort());
	}
	
	/**
	 * 获取执行shell文件命令
	 * @return
	 */
	public String getBatchCmd() {
		return String.format(Constant.CMD_BATCH, 
				this.getIp(), this.getPort(), this.getIp(), this.getPort(), this.getIp(), this.getPort());
//		return String.format("%s %s %s", Constant.SHELL_FILE, this.getIp(), this.getPort());
	}
}
