package me.mos.ti.serializer;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月12日 上午10:39:18
 */
public class SerializerAdapter {

	static class GsonSerializerHolder {
		static final Serializer GSON = new GsonSerializer(false);
		static final Serializer XSTREAM = new XStreamSerializer();
	}

	public static Serializer currentSerializer() {
		return GsonSerializerHolder.GSON;
	}
}