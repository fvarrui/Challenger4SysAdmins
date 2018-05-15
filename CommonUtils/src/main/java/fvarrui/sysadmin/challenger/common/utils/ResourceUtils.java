package fvarrui.sysadmin.challenger.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

public class ResourceUtils {
	
	public static String readResource(String path) {
		String content = "";
		try {
			content = FileUtils.readFileToString(new File(ResourceUtils.class.getResource(path).toURI()), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return content;
	}

}
