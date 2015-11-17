package letv.zookeeper.monitor.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 * @author wangdi5
 *
 */
public class ThreadUtil {
	
	private static volatile ExecutorService executor;
	
	/**
	 * 初始化线程池
	 */
	private static void initExecutor() {
		if(executor == null) {
			synchronized (ThreadUtil.class) {
				if(executor == null) {
					executor = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10000));
				}
			}
		}
	}
	
	/**
	 * 异步执行一个任务
	 * @param task
	 * @return
	 */
	public static <V> Future<V> submit(Callable<V> task) {
		initExecutor();
		
		return executor.submit(task);
	}
	
	/**
	 * 异步执行一个任务
	 * @param task
	 * @return
	 */
	public static Future<?> submit(Runnable task) {
		initExecutor();
		
		return executor.submit(task);
	}
	
    /**
     * 关闭线程池
     */
    public static void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}
