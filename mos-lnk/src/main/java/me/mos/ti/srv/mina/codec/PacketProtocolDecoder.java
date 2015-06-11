package me.mos.ti.srv.mina.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

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
final class PacketProtocolDecoder extends CumulativeProtocolDecoder {

	private static final Logger log = LoggerFactory.getLogger(PacketProtocolDecoder.class);

	final Charset charset;

	/**
	 * 定义头信息的字节数
	 */
	private static final int HEAD_BYTE_LENGTH = 20;

	/**
	 * 在头信息中, 前4位表示该报文的长度, 剩余的16位头信息备用
	 */
	private static final int PACKET_BYTE_LENGTH = 4;
	
	/**
	 * 版本号所在的头位置
	 */
	private static final int VERSION_POSITION = 5;
	
	/**
	 * 版本1.0.0
	 */
	private static final byte VERSION_1_0_0 = 1;

	PacketProtocolDecoder(Charset charset) {
		super();
		this.charset = charset;
	}
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		CharsetDecoder charsetDecoder = charset.newDecoder();
		if (in.remaining() < HEAD_BYTE_LENGTH) {
			return false;// 接收新数据，以拼凑成完整数据
		}
		if (in.remaining() > 0) {
			byte[] head = new byte[HEAD_BYTE_LENGTH];
			in.mark();// 标记当前位置，以便reset
			in.get(head);// 读取头信息
			int length = ByteUtil.toInt(ByteUtil.getBytes(head, 0, PACKET_BYTE_LENGTH));
			byte version = head[VERSION_POSITION - 1];
			log.error("消息版本号[{}].", version);
			if (length - HEAD_BYTE_LENGTH > in.remaining()) {
				// 如果消息内容不够，则重置，相当于不读取length
				in.reset();
				return false;// 接收新数据，以拼凑成完整数据
			}
			byte[] packet = new byte[length];
			in.get(packet, 0, length);
			// 对packet进行转换和解析
			out.write(packet);
			if (in.remaining() > 0) {// 如果读取内容后还粘了包，进行下一次解析
				return true;
			}
		}
		return false;// 处理成功，让父类进行接收下个包
	}
}