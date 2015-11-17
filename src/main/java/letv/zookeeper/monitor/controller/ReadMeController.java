package letv.zookeeper.monitor.controller;

import letv.zookeeper.monitor.exception.SSHException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ReadMeController extends BaseController {
	
	private static final String MODEL_TYPE = "readMe";
	
	private static final String INCLUDE_FILE = MODEL_TYPE + ".ftl";
	
	private static final String REQUEST_MAPPING = "/" + MODEL_TYPE;
	
	@RequestMapping(value=REQUEST_MAPPING)  
    public ModelAndView showReadMe(HttpServletRequest request,HttpServletResponse response) throws SSHException {  
        ModelAndView mav=new ModelAndView();  
        mav.addObject("userName", "userName");
        mav.addObject("menu", MODEL_TYPE); 
        mav.addObject("url", INCLUDE_FILE);
        mav.setViewName("/base");  
    return mav;
    }
}
