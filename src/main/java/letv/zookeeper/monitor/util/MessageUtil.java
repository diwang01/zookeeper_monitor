package letv.zookeeper.monitor.util;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

/**
 * 发送短信接口
 * @author wangdi5
 *
 */
public class MessageUtil {

    private static final String MESSAGE_URL = "http://115.182.51.124:7070/thirdPartner/qxt";

    public static void main(String[] args) {
        System.out.println(MessageUtil.sendApiMessage("18610017997", "sdfdsf"));
    }

    /**
     * 短信发送接口
     * @param phone 短信接收手机号
     * @param message 短信内容
     * @param key 短信发送方标识
     * @return
     */
    public static boolean sendApiMessage(String phone, String message) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(message)) {
            return false;
        }
        try {
            StringBuffer sbf = new StringBuffer();
            sbf.append("corpID=800028").append("&msg=" + URLEncoder.encode(message, "GBK"))
                    .append("&srcAddr=9513129212102").append("&destAddr=" + phone);

            String back = HttpUtilPost.readContent(MESSAGE_URL, sbf.toString());
            System.out.println(back);
            if (StringUtils.isNotBlank(back) && back.contains("OK") || back.contains("ok")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
