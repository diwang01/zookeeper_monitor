package letv.zookeeper.monitor.exception;

/**
 * SShException
 * @author wangdi5
 *
 */
public class SSHException extends Exception {


    private static final long serialVersionUID = -1822890239034070693L;

    public SSHException() {
		super();
	}
	
	public SSHException(String message) {
		super(message);
	}
	
	public SSHException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
