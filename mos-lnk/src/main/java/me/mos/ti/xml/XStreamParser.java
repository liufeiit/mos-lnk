package me.mos.ti.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午7:12:47
 */
public class XStreamParser {

	private static class XStreamHolder {
		private static final XStream X = new XStream();
		private static final Pattern P = Pattern.compile("\\s{2,}|\t|\r|\n");
		static {
			X.autodetectAnnotations(true);
		}
	}

	public static <T> String toXML(T object) {
		Class<? extends Object> type = object.getClass();
		XStreamAlias alias = type.getAnnotation(XStreamAlias.class);
		if (alias != null) {
			XStreamHolder.X.alias(alias.value(), type);
		}
		String xml = XStreamHolder.X.toXML(object);
		Matcher m = XStreamHolder.P.matcher(xml);
		return m.replaceAll(StringUtils.EMPTY);
	}

	public static <T> T toObj(Class<T> clazz, String xml) {
		XStreamAlias alias = clazz.getAnnotation(XStreamAlias.class);
		if (alias != null) {
			XStreamHolder.X.alias(alias.value(), clazz);
		}
		return clazz.cast(XStreamHolder.X.fromXML(xml));
	}
}