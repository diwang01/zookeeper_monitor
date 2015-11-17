package letv.zookeeper.monitor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import letv.zookeeper.monitor.constant.GlobalInstance;
import letv.zookeeper.monitor.exception.SSHException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 集群机器监控
 * @author wangdi5
 *
 */
@Controller
public class HostPerformanceController extends BaseController {
	
	private static final String MODEL_TYPE = "hostPerformance";
	
	private static final String INCLUDE_FILE = MODEL_TYPE + ".ftl";
	
	private static final String REQUEST_MAPPING = "/" + MODEL_TYPE;
	
	@RequestMapping(value=REQUEST_MAPPING)
    public ModelAndView showZkServersMonitorInfo(HttpServletRequest request,HttpServletResponse response) throws SSHException {  
		ModelAndView mav=new ModelAndView(); 
		
        mav.addObject("hostPerformanceInfoList", GlobalInstance.hostPerformanceInfoList);
        mav.addObject("dateStrHostPerformance", GlobalInstance.dateStrHostPerformance);
        mav.addObject("userName", "userName"); 
        mav.addObject("menu", MODEL_TYPE); 
        mav.addObject("message", "");
        mav.addObject("url", INCLUDE_FILE);
        mav.setViewName("/base");  
        return mav;
    }
}