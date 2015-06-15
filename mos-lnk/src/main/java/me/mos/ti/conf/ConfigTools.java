package me.mos.ti.conf;

import java.io.FileInputStream;
import java.nio.charset.Charset;

import me.mos.ti.serializer.SerializerUtils;
import me.mos.ti.utils.StreamUtils;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月15日 下午1:41:57
 */
public class ConfigTools {

	public static <T> T conf(Class<T> type, Charset charset) {
		try {
			Resource resource = type.getAnnotation(Resource.class);
			if(resource == null) {
				throw new IllegalStateException("Not found Resource on class : " + type);
			}
			return SerializerUtils.xstream().deserialize(type, StreamUtils.copyToString(new FileInputStream(resource.location()), charset));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}