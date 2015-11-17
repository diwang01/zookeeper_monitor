package letv.zookeeper.monitor.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 前台展示树形节点结构
 * @author wangdi5
 *
 */
public class NodeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String text;
	
	private String state;
	
	private List<NodeInfo> children;
	
	private Map<String, String> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<NodeInfo> getChildren() {
		return children;
	}

	public void setChildren(List<NodeInfo> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
