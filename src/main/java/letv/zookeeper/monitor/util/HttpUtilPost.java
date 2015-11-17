package letv.zookeeper.monitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * POST方式传数据
 * @author 
 * 
 */
public final class HttpUtilPost {

	private static final Log log = LogFactory.getLog(HttpUtilPost.class);

	public static final String readContent(String url,String parameter) {
		//return readContend(url, parameter, 60000, 60000);
		String content="";
	    int count=0;
		while(true){
			count++;
			try{
			    content = readContend(url,parameter, 800, 800);
			}catch(Exception e){
				content = readContend(url,parameter, 800, 800);	//
			}
			if((StringUtils.isNotBlank(content) &&!content.equals("-1")) ||count==5){
				break;
			}
		}
		return content;
	}

	public static final String readContend(String url, String parameter, int contimeout, int readtimeout) {
		URL newurl = null;
		BufferedReader reader = null;
		StringBuffer sb = null;
		HttpURLConnection connection = null;
		try {
			newurl = new URL(url);
			connection = (HttpURLConnection) newurl.openConnection();
			connection.setConnectTimeout(contimeout);
			connection.setReadTimeout(readtimeout);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
			connection.setDoInput(true);
			connection.connect();
			
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(parameter);  
			out.flush(); 
			out.close(); 
			
			InputStream stream = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			reader.close();
			connection.disconnect();
			return sb.toString();
		} catch (IOException e) {
			log.error("连接url: " + url + "错误", e);
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					log.error("读关闭错误", e1);
				}
			}
			//e.printStackTrace();
			return "-1";
		} finally {
			url = null;
			if (connection != null)
				connection.disconnect();
		}
	}
}
