package fvarrui.sysadmin.challenger.common.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
	
	private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{([a-zA-Z0-9\\.]+)\\}");
	
	public static String translate(String string, Map<String, Object> map) {
		Matcher m = VARIABLE_PATTERN.matcher(string);
		while (m.find()) {
			String variable = Pattern.quote(m.group());
			String name = m.group(1);
			if (map.get(name) == null) continue;
			String value = Matcher.quoteReplacement(map.get(name).toString());
			string = string.replaceAll(variable, value);
		}
		return string;
	}
	
	public static Map<String, Object> translate(Map<String, Object> map) {
		Map<String, Object> translatedMap = new HashMap<>(map);
		for (String key : translatedMap.keySet()) {
			Object value = translatedMap.get(key);
			if (value instanceof String) {
				String newValue = translate(value.toString(), translatedMap);
				translatedMap.put(key, newValue);
			}
		}
		return translatedMap;
	}
	
	public static Map<String, Object> getMapFromProperties(Properties p) {
		return populateMapFromProperties(new HashMap<>(), p);
	}
	
	public static Map<String, Object> populateMapFromProperties(Map<String, Object> data, Properties p) {
		p.stringPropertyNames().stream().forEach(key -> data.put(key, p.getProperty(key)));
		return data;
	}
	
	public static void main(String[] args) {
		Chronometer chrono = new Chronometer();
		
		Properties properties = new Properties(System.getProperties());
		properties.put("RUTA", "Mi ruta es ${user.home} y mi sostema ${os.name}");
		properties.put("VALOR", "El valor ${de} RUTA es ${RUTA}");
		properties.put("ARQUITECTURA", "Mi arquitectura ${os.arch}");
		properties.put("IDIOMA", "Mi idioma es ${user.language}");
		
		Map<String, Object> translated = translate(getMapFromProperties(properties));
		
		translated.forEach((k,v) -> System.out.println(k + "=" + v));
		
		System.out.println(chrono.stop() + "ms");
	}

}
