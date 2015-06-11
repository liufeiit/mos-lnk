package me.mos.ti.srv.mina.codec;

import java.nio.charset.Charset;

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
	
	PacketProtocolDecoder(Charset charset) {
		super();
		this.charset = charset;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		return false;
	}
}