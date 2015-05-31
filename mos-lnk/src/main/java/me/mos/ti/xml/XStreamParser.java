package me.mos.ti.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午7:12:47
 */
public class XStreamParser {

	public static <T> String toXML(T object) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		Class<? extends Object> type = object.getClass();
		XStreamAlias alias = type.getAnnotation(XStreamAlias.class);
		if (alias != null) {
			xstream.alias(alias.value(), type);
		}
		return xstream.toXML(object);
	}

	public static <T> T toObj(Class<T> clazz, String xml) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		XStreamAlias alias = clazz.getAnnotation(XStreamAlias.class);
		if (alias != null) {
			xstream.alias(alias.value(), clazz);
		}
		return clazz.cast(xstream.fromXML(xml));
	}
}