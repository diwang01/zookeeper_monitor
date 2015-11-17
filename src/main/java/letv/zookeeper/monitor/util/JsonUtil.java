package letv.zookeeper.monitor.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * JSON与实体bean之间转换
 * 
 * @author liming
 * @version 2013-07-22
 * 
 */
public class JsonUtil {
	private final static ObjectMapper mapper = new ObjectMapper();

	/**
	 * bean 2 JSON
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String beanToJson(Object obj) throws IOException {
		StringWriter writer = new StringWriter();
		JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
		mapper.writeValue(gen, obj);
		gen.close();
		String json = writer.toString();
		writer.close();
		return json;
	}

	/**
	 * JSON 2 BEAN
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToBean(String json, Class<T> cls) throws Exception {
		T vo = mapper.readValue(json, cls);
		return vo;
	}

	public static <T> T json2Object(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		// mapper.getDeserializationConfig().without(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		// return (T) mapper.readValue(json, clazz);
		T t = null;
		mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		t = (T) mapper.readValue(json, clazz);
		return t;

	}

	/**
	 * 根据json获得list example: json2list(json,Song.class)
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */

	public static <T> List<T> json2list(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return Collections.emptyList();
		}
		List<T> result = new ArrayList<T>();
		try {
			Field[] fields = clazz.getDeclaredFields();
			Field[] parentFields = null;
			Class parentClazz = clazz.getSuperclass();
			if (parentClazz != null) {
				parentFields = parentClazz.getDeclaredFields();
			}
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, Object>> list = mapper.readValue(json, List.class);
			for (LinkedHashMap<String, Object> objectMap : list) {
				T pojo = (T) clazz.newInstance();
				for (Entry<String, Object> entry : objectMap.entrySet()) {
					if (parentFields != null) {
						for (Field field : parentFields) {
							if (field.getName().equals(entry.getKey())) {
								field.setAccessible(true);
								if (!field.isEnumConstant()) {
									field.set(pojo, entry.getValue());
								}
							}
						}
					}
					for (Field field : fields) {
						if (field.getName().equals(entry.getKey())) {
							field.setAccessible(true);
							if (!field.isEnumConstant()) {
								field.set(pojo, entry.getValue());
							}
						}
					}
				}
				result.add(pojo);
			}
		} catch (Exception e) {
		}
		return result;

	}

	/**
	 * 是否JSON格式
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isJsonObject(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			JSONObject.fromObject(json);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
