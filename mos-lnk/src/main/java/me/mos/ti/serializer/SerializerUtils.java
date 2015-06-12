package me.mos.ti.serializer;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年6月12日 上午10:39:18
 */
public class SerializerUtils {

	private static class GsonSerializerHolder {
		private static final Serializer GSON = new GsonSerializer(false);
		private static final Serializer XS = new XStreamSerializer();
	}

	public static Serializer gson() {
		return GsonSerializerHolder.GSON;
	}

	public static Serializer xstream() {
		return GsonSerializerHolder.XS;
	}
}