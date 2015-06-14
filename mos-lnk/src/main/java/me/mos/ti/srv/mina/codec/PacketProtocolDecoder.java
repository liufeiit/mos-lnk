package me.mos.ti.srv.mina.codec;

import java.nio.charset.Charset;

import me.mos.ti.packet.InPacket;
import me.mos.ti.parser.PacketParser;
import me.mos.ti.srv.PacketProtocol;
import me.mos.ti.srv.Version;
import me.mos.ti.utils.ByteUtil;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 累计报文编码器, 读取完整的报文信息.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * 
 * @version 1.0.0
 * @since 2015年6月11日 下午5:42:42
 */
final class PacketProtocolDecoder extends CumulativeProtocolDecoder implements PacketProtocol {

	private static final Logger log = LoggerFactory.getLogger(PacketProtocolDecoder.class);

	private final Charset charset;

	private final PacketParser parser;

	PacketProtocolDecoder(Charset charset, PacketParser parser) {
		super();
		this.charset = charset;
		this.parser = parser;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() < HEAD_BYTE_LENGTH) {
			return false;// 接收新数据，以拼凑成完整数据
		}
		if (in.remaining() > 0) {
			byte[] head = new byte[HEAD_BYTE_LENGTH];
			in.mark();// 标记当前位置，以便reset
			in.get(head);// 读取头信息
			int length = ByteUtil.toInt(ByteUtil.getBytes(head, 0, PACKET_BYTE_LENGTH));
			if (length - HEAD_BYTE_LENGTH > in.remaining()) {
				// 如果消息内容不够，则重置，相当于不读取length
				in.reset();
				return false;// 接收新数据，以拼凑成完整数据
			}
			byte[] packetBytes = new byte[length];
			in.get(packetBytes, 0, length);
			// 对packet进行转换和解析
			byte version = head[VERSION_POSITION - 1];
			log.error("消息版本号[{}].", Version.parse(version));
			String packetString = new String(packetBytes, charset);
			log.error("Incoming Packet : {}", packetString);
			try {
				InPacket inPacket = parser.parse(packetString);
				out.write(inPacket);
			} catch (Throwable e) {
				log.error("Incoming Packet Parse Error【" + packetString + "】.", e);
				return false;
			}
			if (in.remaining() > 0) {// 如果读取内容后还粘了包，进行下一次解析
				return true;
			}
		}
		return false;// 处理成功，让父类进行接收下个包
	}
}