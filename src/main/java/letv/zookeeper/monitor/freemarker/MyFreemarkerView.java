package letv.zookeeper.monitor.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 重写视图解析器，获取项目根路径
 * @author wangdi5
 *
 */
public class MyFreemarkerView extends FreeMarkerView {

	private static final String CONTEXT_PATH = "base";
	
	@Override
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		model.put(CONTEXT_PATH, request.getContextPath());
		super.exposeHelpers(model, request);
	}
	
}
