package letv.zookeeper.monitor.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 常量
 * @author wangdi5
 *
 */
public class Constant {

    public static final String BR = "<br/>";

    public static final String EMPTY_STRING = "";

    //thread睡眠间隔
    public static final int THREAD_SLEEP_TIME = 30000;

    //监控zookeeper服务命令
    public static final String CMD_RUOK = "echo ruok | nc %s %s";

    //监控zookeeper服务机器性能信息
    public static final String CMD_TOP = "top -b -n 1 | head -5";

    //监控zookeeper硬盘容量信息
    public final static String CMD_DF_LH = "df -lh";

    //批量zookeeper命令
    public final static String CMD_BATCH = "echo ruok | nc %s %s;echo stat | nc %s %s;echo wchs | nc %s %s";

    //短信提醒内容
    public static final String MESSAGE = "ip=%s, port=%s的zookeeper服务未能正常工作，请查看！";

    //手机短信列表
    public static final List<String> phoneNumList = Arrays.asList(new String[] { "18698621416" });
}
