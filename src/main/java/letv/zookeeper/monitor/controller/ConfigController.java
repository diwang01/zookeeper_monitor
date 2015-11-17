package letv.zookeeper.monitor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import letv.zookeeper.monitor.exception.SSHException;
import letv.zookeeper.monitor.model.ZkClusterInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 集群配置
 * @author wangdi5
 *
 */
@Controller
public class ConfigController extends BaseController {
	
	private static final String MODEL_TYPE = "conf";
	
	private static final String INCLUDE_FILE = MODEL_TYPE + ".ftl";
	
	private static final String REQUEST_MAPPING = "/" + MODEL_TYPE;
	
	@RequestMapping(value=REQUEST_MAPPING)
    public ModelAndView showZkServerConf(HttpServletRequest request,HttpServletResponse response) throws SSHException {  
		ZkClusterInfo zkClusterInfo = zkClusterDao.getClusterInfo();
		
        ModelAndView mav=new ModelAndView();  
        mav.addObject("zkClusterInfo", zkClusterInfo);
        mav.addObject("userName", "userName"); 
        mav.addObject("menu", MODEL_TYPE); 
        if(zkClusterInfo == null) {
        	mav.addObject("message", "请配置zookeeper集群信息");
        } else {
        	mav.addObject("message", "此处可以修改zookeeper集群信息");
        }
        mav.addObject("url", INCLUDE_FILE);
        mav.setViewName("/base");  
        return mav;
    }
	
	@RequestMapping(value=REQUEST_MAPPING + "/update")
    public ModelAndView updateZkServerConf(HttpServletRequest request,HttpServletResponse response, ZkClusterInfo info) throws SSHException {  
		ModelAndView mav=new ModelAndView();
		
		if(info.getClusterId() == 0) {
			zkClusterDao.insertClusterInfo(info);
			mav.addObject("message", "添加zookeeper集群信息成功");
		} else {
			zkClusterDao.updateClusterInfo(info);
			mav.addObject("message", "修改zookeeper集群信息成功");
		}
		
		//重置zookeeper配置信息
		this.resetZkClient();
		
		//重置zkModelInfoList信息
		this.resetZkModelinfoList();
		
        mav.addObject("zkClusterInfo", info);
        mav.addObject("userName", "userName"); 
        mav.addObject("menu", MODEL_TYPE); 
        mav.addObject("url", INCLUDE_FILE);
        mav.setViewName("/base");
        
        return mav;
    }
}
