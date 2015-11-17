package letv.zookeeper.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import letv.zookeeper.monitor.exception.SSHException;
import letv.zookeeper.monitor.model.NodeInfo;
import letv.zookeeper.monitor.util.JsonUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 遍历zookeeper所有节点信息
 * @author wangdi5
 *
 */
@Controller
public class ExplorerController extends BaseController {
    private static final String MODEL_TYPE = "explorer";

    private static final String INCLUDE_FILE = MODEL_TYPE + ".ftl";

    private static final String REQUEST_MAPPING = "/" + MODEL_TYPE;

    @RequestMapping(value = REQUEST_MAPPING)
    public ModelAndView showZkZnodeInfo(HttpServletRequest request, HttpServletResponse response) throws SSHException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("userName", "userName");
        mav.addObject("menu", MODEL_TYPE);
        mav.addObject("url", INCLUDE_FILE);
        mav.setViewName("/base");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/getData")
    public String getZkPathZnodeData(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = true, value = "path") String path) throws Exception {
        CuratorFramework client = this.getZkClient();

        Map<String, Object> result = new HashMap<String, Object>();
        byte[] dataByte = client.getData().forPath(path);

        if (dataByte != null) {
            result.put("data", new String(dataByte));
        } else {
            result.put("data", "");
        }

        return JsonUtil.beanToJson(result);
    }

    @ResponseBody
    @RequestMapping(value = "/delData")
    public String deleteZkPathZnodeData(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = true, value = "path") String path) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        if ("/".equals(path)) {
            result.put("flag", false);
            return JsonUtil.beanToJson(result);
        }

        CuratorFramework client = this.getZkClient();

        result.put("flag", this.traverseNodes(client, path));

        return JsonUtil.beanToJson(result);
    }

    public boolean traverseNodes(CuratorFramework client, String rootPath) {
        if (StringUtils.isBlank(rootPath)) {
            return false;
        }

        List<String> pathList = null;

        try {
            pathList = client.getChildren().forPath(rootPath);

            String pathValue = null;

            for (String path : pathList) {
                if ("/".equals(rootPath)) {
                    pathValue = rootPath + path;
                } else {
                    pathValue = rootPath + "/" + path;
                }

                List<String> children = client.getChildren().forPath(pathValue);
                if (children != null && children.size() > 0) {
                    this.traverseNodes(client, pathValue);
                } else {
                    client.delete().forPath(pathValue);
                }
            }

            client.delete().forPath(rootPath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/getPath")
    public String getZkPathZnodeInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(required = true, value = "id") String id) throws Exception {
        CuratorFramework client = this.getZkClient();

        String rootPath = id;

        if (id.equals("00")) {
            id = "/";
        }

        String[] idArry = id.split(",");
        id = idArry[idArry.length - 1];

        List<String> list = client.getChildren().forPath(id);
        List<NodeInfo> nodeList = new ArrayList<NodeInfo>();
        for (String str : list) {
            NodeInfo node = new NodeInfo();
            if (id.equals("/")) {
                node.setId(id + str);
            } else {
                node.setId(id + "/" + str);
            }
            node.setText(str);
            if (CollectionUtils.isEmpty(client.getChildren().forPath(node.getId()))) {
                node.setState("open");
            } else {
                node.setState("closed");
            }

            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("url", node.getId());
            node.setAttributes(attributes);

            nodeList.add(node);
        }

        List<NodeInfo> rootList = new ArrayList<NodeInfo>();
        if (rootPath.equals("00")) {
            NodeInfo node = new NodeInfo();
            node.setId("/");
            node.setText("/");
            node.setState("closed");
            rootList.add(node);
            node.setChildren(nodeList);
            return JsonUtil.beanToJson(rootList);
        }

        return JsonUtil.beanToJson(nodeList);
    }
}
