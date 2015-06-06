package me.mos.ti.packet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import me.mos.ti.xml.XStreamParser;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月6日 下午6:08:40
 */
@XStreamAlias(PacketAlias.ONLINEUSER_NAME)
public class OnlineUser implements Packet {

	@XStreamImplicit(itemFieldName = "u")
	private List<String> us = new ArrayList<String>();

	public OnlineUser(List<String> us) {
		super();
		if (!CollectionUtils.isEmpty(us)) {
			this.us.addAll(us);
		}
	}

	public List<String> getUs() {
		return us;
	}

	public void setUs(List<String> us) {
		this.us = us;
	}

	@Override
	public String toXML() {
		return XStreamParser.toXML(this);
	}

	@Override
	public Type getType() {
		return Type.OnlineUser;
	}
}