package me.mos.ti.xml;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年5月30日 下午7:12:47
 */
public class XStreamParser {

	private final static Logger log = LoggerFactory.getLogger(XStreamParser.class);
	
	public static <T> String toXML(T object, String alias) {
		try {
			XStream xstream = new XStream();
			xstream.autodetectAnnotations(true);
			xstream.alias(alias, object.getClass());
			return xstream.toXML(object);
		} catch (Exception e) {
			log.error("XStream Parse to XML Error.", e);
		}
		return StringUtils.EMPTY;
	}
	
	public static <T> T toObj(Class<T> clazz, String xml, String alias) {
		try {
			XStream xstream = new XStream();
			xstream.autodetectAnnotations(true);
			xstream.alias(alias, clazz);
			return clazz.cast(xstream.fromXML(xml));
		} catch (Exception e) {
			log.error("XStream Parse to Obj Error.", e);
		}
		return null;
	}
}
